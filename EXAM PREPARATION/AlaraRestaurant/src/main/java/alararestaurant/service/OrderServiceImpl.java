package alararestaurant.service;

import alararestaurant.domain.dtos.OrderDto;
import alararestaurant.domain.dtos.OrderItemDto;
import alararestaurant.domain.dtos.RootOrderDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    public final static String ORDER_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\orders.xml";

    private final OrderRepository orderRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, EmployeeRepository employeeRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDER_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(RootOrderDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        RootOrderDto rootOrderDtos = (RootOrderDto) unmarshaller.unmarshal(new File(ORDER_FILE_PATH));

        for (OrderDto orderDto : rootOrderDtos.getOrderDtos()) {
            Order order = this.modelMapper.map(orderDto, Order.class);
            Employee employee = this.employeeRepository.findByName(orderDto.getEmployee());

            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemDto orderItemDto : orderDto.getRootOrderItemDto().getOrderItemDtos()) {
                Item item = this.itemRepository.findByName(orderItemDto.getName());
                if (employee == null || item.getName() == null){
                    continue;
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setItem(item);
                orderItem.setQuantity(orderItemDto.getQuantity());
                orderItems.add(orderItem);
            }

            order.setOrderItems(orderItems);
            order.setEmployee(employee);
            this.orderRepository.saveAndFlush(order);
            sb.append(String.format("Order for %s on %s added", order.getCustomer(), order.getDateTime())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();

        List<Order> orders = this.orderRepository.findAllByFinishesFlippers();

        for (Order order : orders) {
            sb.append(String.format("Name: %s\n" +
                    "Orders:\n  " +
                    "   Customer: %s\n" +
                    "   Items:\n", order.getEmployee().getName(), order.getCustomer()));
            for (OrderItem orderItem : order.getOrderItems()) {
                sb.append(String.format("      Name: %s\n" +
                        "      Price: %s\n" +
                        "      Quantity: %d\n", orderItem.getItem().getName(), orderItem.getItem().getPrice(), orderItem.getQuantity())).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
