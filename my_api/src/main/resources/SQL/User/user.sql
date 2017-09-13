

###最初使用的
#sql("c")
    select  b.id
    from xk_business  b
    where b.latitude <> 0  and b.longitude > #p(zero)  and b.longitude < #p(one)  and b.latitude > #p(two)  and b.latitude < #p(three)
    order by
    ACOS(
      SIN(( #p(latitude) * 3.1415) / 180 ) * SIN((b.latitude * 3.1415) / 180 ) + COS(( #p(latitude) * 3.1415 ) / 180 ) * COS((b.latitude * 3.1415) / 180 ) * COS(( #p(longitude) * 3.1415 ) / 180 - (b.longitude * 3.1415) / 180 )
    ) * 6380
    asc
#end


多行
#--

--#

###算法方式一：能实现功能，用db.paginate不能实现复杂的orderBy语句 ，换成paginateByFullSql 但是不支持sql temple 只能再代码中拼接(而且不能以参数形式传入，只能直接拼接)
#sql("byDistanceStore")
    select  b.*,
    ROUND(
        6378.138 * 2 * ASIN(
            SQRT(
                POW(
                    SIN(
                        (
                            #p(latitude) * PI() / 180 -  b.latitude* PI() / 180
                        ) / 2
                    ),
                    2
                ) + COS(#p(latitude) * PI() / 180) * COS( b.latitude* PI() / 180) * POW(
                    SIN(
                        (
                           #p(longitude) * PI() / 180 -    b.longitude * PI() / 180
                        ) / 2
                    ),
                    2
                )
            )
        ) * 1000
    ) AS distance
    from xk_business  b
    where b.latitude <> 0  and b.longitude > #p(zero)  and b.longitude < #p(one)  and b.latitude > #p(two)  and b.latitude < #p(three)
    order by distance ASC
#end


###子查询 距离排序
#define distance()
   select  b.id bussinessId , b.storeName, b.area , b.address , b.longitude  , b.latitude ,truncate(b.sumScore/b.numberMark,2) avgScore,
    ROUND(
        6378.138 * 2 * ASIN(
            SQRT(
                POW(
                    SIN(
                        (
                            #p(latitude) * PI() / 180 -  b.latitude* PI() / 180
                        ) / 2
                    ),
                    2
                ) + COS(#p(latitude) * PI() / 180) * COS( b.latitude* PI() / 180) * POW(
                    SIN(
                        (
                           #p(longitude) * PI() / 180 -    b.longitude * PI() / 180
                        ) / 2
                    ),
                    2
                )
            )
        ) * 1000
    ) AS distance
    from xk_business  b
#end

#define distanceWhere()
        where b.latitude <> 0  and b.longitude > #p(zero)  and b.longitude < #p(one)  and b.latitude > #p(two)  and b.latitude < #p(three)
#end

###距离排序 统计sql
#sql("totalDistance")
    select count(*)  FROM
    (
      #@distance()
      #@distanceWhere()
      order by distance ASC ,b.sumScore/b.numberMark DESC
    ) a
#end

#距离排序 总sql
#sql("findDistance")
    #@distance()
    #@distanceWhere()
    order by distance ASC ,b.sumScore/b.numberMark DESC
#end

###根据评分排序
#sql("totalScoreDistance")
    select count(*)  FROM
    (
      #@distance()
      order by b.sumScore/b.numberMark DESC ,distance ASC
    ) a
#end

#评分排序 总sql
#sql("findScoreDistance")
    #@distance()
    order by b.sumScore/b.numberMark DESC ,distance ASC
#end


###根据市区查询附近门店
#sql("totalAreaDistance")
    select count(*)  FROM
    (
      #@distance()
      where b.city = #p(city) AND b.area = #p(area)
      order by b.sumScore/b.numberMark DESC ,distance ASC
    ) a
#end

#地区排序 总sql
#sql("findAreaDistance")
    #@distance()
    where b.city = #p(city) AND b.area = #p(area)
    order by b.sumScore/b.numberMark DESC ,distance ASC
#end


###根据服务类型查询附近门店
#sql("totalServiceDistance")
    select count(*)  FROM
    (
      #@distance()
      LEFT JOIN xk_service s
      ON b.id = s.businessId
      where s.categoryId = #p(categoryId)
      order by b.sumScore/b.numberMark DESC
    ) a
#end

#服务类型排序 总sql
#sql("findServiceDistance")
    #@distance()
    LEFT JOIN xk_service s
    ON b.id = s.businessId
    where s.categoryId = #p(categoryId)
    order by b.sumScore/b.numberMark DESC
#end



#用户收藏门店列表
#sql("starStoreList")
  select s.id businessId,s.storeName,s.businessLogo,truncate(s.sumScore/s.numberMark,2),
          s.province,s.city,s.area,s.address,us.id collectId
  from xk_business s,xk_user_store us
  where us.businessId = s.id and us.userId = #p(userId)
  order by us.createTime desc
#end



#--
注：算法方式一：能实现功能，用db.paginate不能实现复杂的orderBy语句 ，换成paginateByFullSql 但是不支持sql temple 只能再代码中拼接(而且不能以参数形式传入，只能直接拼接)
###根据市区查询附近门店
#sql("areaStore")
    select DISTINCT b.id bussinessId , b.storeName, b.area , b.address , b.longitude  , b.latitude , b.sumScore/b.numberMark avgScore
    from xk_business b
    where b.city = ? AND b.area = ?
    order by b.sumScore/b.numberMark DESC
#end


###根据服务类型查询有该服务的门店
#sql("serviceStore")
    select DISTINCT b.id bussinessId , b.storeName, b.area , b.address , b.longitude  , b.latitude , b.sumScore/b.numberMark avgScore
    from xk_business b  LEFT JOIN xk_service s
    ON b.id = s.businessId
    where s.categoryId = ?
    order by b.sumScore/b.numberMark DESC
#end

--#

