package com.dao;

import com.entity.DingdanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.entity.vo.StatisticsVO;
import org.apache.ibatis.annotations.Param;
import com.entity.vo.DingdanVO;
import com.entity.view.DingdanView;


/**
 * 订单
 *
 * @author
 * @email
 * @date 2020-09-23 18:00:25
 */
public interface DingdanDao extends BaseMapper<DingdanEntity> {

	List<DingdanVO> selectListVO(@Param("ew") Wrapper<DingdanEntity> wrapper);

	DingdanVO selectVO(@Param("ew") Wrapper<DingdanEntity> wrapper);

	List<DingdanView> selectListView(@Param("ew") Wrapper<DingdanEntity> wrapper);

	List<DingdanView> selectListView(Pagination page,@Param("ew") Wrapper<DingdanEntity> wrapper);

	DingdanView selectView(@Param("ew") Wrapper<DingdanEntity> wrapper);

	List<StatisticsVO> qryCount();

	List<StatisticsVO> qrySaleCount(@Param("start") String start,@Param("end") String end);

}
