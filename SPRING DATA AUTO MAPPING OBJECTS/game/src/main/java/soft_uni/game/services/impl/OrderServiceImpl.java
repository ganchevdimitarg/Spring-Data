package soft_uni.game.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.game.repositories.OrderRepositiry;
import soft_uni.game.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepositiry orderRepositiry;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public OrderServiceImpl(OrderRepositiry orderRepositiry) {
        this.orderRepositiry = orderRepositiry;
        this.modelMapper = new ModelMapper();
    }



}
