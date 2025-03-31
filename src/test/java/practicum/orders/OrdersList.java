package practicum.orders;

import java.util.List;

public class OrdersList {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public OrdersList(){}

    public OrdersList(List<Order> orders, PageInfo pageInfo, List<AvailableStations> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }


    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
    }

}
