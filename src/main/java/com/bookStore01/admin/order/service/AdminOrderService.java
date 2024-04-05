package com.bookStore01.admin.order.service;

import java.util.List;
import java.util.Map;

import com.bookStore01.order.vo.OrderVO;

public interface AdminOrderService {
	public List<OrderVO>listNewOrder(Map condMap) throws Exception;
	public void  modifyDeliveryState(Map deliveryMap) throws Exception;
	public Map orderDetail(int order_id) throws Exception;
}
