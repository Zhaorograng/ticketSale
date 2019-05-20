package com.linxf.order;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 订单页面vo对象
 *
 * @author lintao
 * @date 2019/5/20
 */
@Data
public class OrderVo {

    @NotEmpty(message = "车次编号不能为空")
    private String tid;// 车次编号

    @NotEmpty(message = "出发站不能为空")
    private String startStation;// 出发站

    @NotEmpty(message = "出发日期不能为空")
    private Date startTime;// 出发日期

    @NotEmpty(message = "到达站不能为空")
    private String endStation;// 到达站
}
