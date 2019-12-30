package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RootOrderDto implements Serializable {

    @XmlElement(name = "order")
    private List<OrderDto> orderDtos;

    public RootOrderDto() {
    }

    public List<OrderDto> getOrderDtos() {
        return orderDtos;
    }

    public void setOrderDtos(List<OrderDto> orderDtos) {
        this.orderDtos = orderDtos;
    }
}
