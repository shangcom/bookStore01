package com.bookStore01.cart.controller;

import java.util.List;
import java.util.Map;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore01.cart.service.CartService;
import com.bookStore01.cart.vo.CartVO;
import com.bookStore01.common.base.BaseController;
import com.bookStore01.member.vo.MemberVO;

@Controller("cartController")
@RequestMapping(value="/cart")
public class CartControllerImpl extends BaseController implements CartController{
	@Autowired
	private CartService cartService;
	@Autowired
	private CartVO cartVO;
	@Autowired
	private MemberVO memberVO;
	
	@RequestMapping(value="/myCartList.do" ,method = RequestMethod.GET)
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		cartVO.setMember_id(member_id);
		Map<String ,List> cartMap=cartService.myCartList(cartVO);
		session.setAttribute("cartMap", cartMap);//��ٱ��� ��� ȭ�鿡�� ��ǰ �ֹ� �� ����ϱ� ���ؼ� ��ٱ��� ����� ���ǿ� �����Ѵ�.
		//mav.addObject("cartMap", cartMap);
		return mav;
	}
	@RequestMapping(value="/addGoodsInCart.do" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		
		cartVO.setMember_id(member_id);
		//īƮ ������� �̹� ��ϵ� ��ǰ���� �Ǻ��Ѵ�.
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartVO);
		System.out.println("isAreadyExisted:"+isAreadyExisted);
		if(isAreadyExisted==true){
			return "already_existed";
		}else{
			cartService.addGoodsInCart(cartVO);
			return "add_success";
		}
	}
	
	@RequestMapping(value="/modifyCartQty.do" ,method = RequestMethod.POST)
	public @ResponseBody String  modifyCartQty(@RequestParam("goods_id") int goods_id,
			                                   @RequestParam("cart_goods_qty") int cart_goods_qty,
			                                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
		cartVO.setCart_goods_qty(cart_goods_qty);
		boolean result=cartService.modifyCartQty(cartVO);
		
		if(result==true){
		   return "modify_success";
		}else{
			  return "modify_failed";	
		}
		
	}
	
	@RequestMapping(value="/removeCartGoods.do" ,method = RequestMethod.POST)
	public ModelAndView removeCartGoods(@RequestParam("cart_id") int cart_id,
			                          HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav=new ModelAndView();
		cartService.removeCartGoods(cart_id);
		mav.setViewName("redirect:/cart/myCartList.do");
		return mav;
	}
}
