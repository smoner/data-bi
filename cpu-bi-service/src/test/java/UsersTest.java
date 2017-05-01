import basetest.SpringTestCase;
import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceEntity;
import com.yonyou.yuncai.cpu.bi.service.price.IBiPriceService;
import org.json.JSONObject;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by fengjqc on 2017/3/20.
 */
public class UsersTest  extends SpringTestCase {

    @Resource
    private IBiPriceService iBiPriceService;

//    @Test
//    public void tempTableTest(){
//        try {
//            tempTableTool.createTempTable();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void transactionTest(){
        BiPriceEntity biPriceEntity = new BiPriceEntity();
        biPriceEntity.setVmaterialId("20170410444444444");
        iBiPriceService.tempTableTest(biPriceEntity);
    }

//    @Test
//    public void testSelectBatchByList(){
//        List<BiPriceEntity> list = new ArrayList<BiPriceEntity>();
//        String[] vsrcMark = new String[]{"1001E410000000003T1Y","1001E410000000003T20","1001E410000000003T22"};
//        BiPriceEntity bi =null;
//        for (String id:vsrcMark) {
//            bi= new BiPriceEntity();
//            bi.setVsrcMark(id);
//            list.add(bi);
//        }
//        List<BiPriceEntity> list2 = iBiPriceService.selectBatchByList(list);
//        String s = null;
//    }
//    @Test
//    public void selectBatchByDefSql(){
//        String sql = " vsrc_mark in ( '1001E410000000003T1Y','1001E410000000003T20') " ;
//        String sql2 =
//        "select * from cpu_biprice where vsrc_mark in ( '1001E410000000003T1Y','1001E410000000003T20')";
//        String sql3 = " select * from cpu_biprice where jsupply is not null and (jsupply->>'aaa')::int>1 ";
//        List<BiPriceEntity> list2 = iBiPriceService.selectBatchByDefSql(sql3);
//        if(list2!=null&&list2.size()>0){
//            for (BiPriceEntity bi:list2) {
//                JSONObject jsonobject = bi.getJsupply();
//                Set<String> set = jsonobject.keySet();
//                Iterator<String> s = jsonobject.keys();
//                s.toString();
//                if (jsonobject!=null){
//                    System.out.println(jsonobject.toString());
//                }
//                JSONObject jpur = bi.getJpurchase();
//                if (jpur!=null){
//                    System.out.println(jpur.toString());
//                }
//                JSONObject jmaterial = bi.getJmaterial();
//                if (jmaterial!=null){
//                    System.out.println(jmaterial.toString());
//                }
//                JSONObject jcondition = bi.getJcondition();
//                if (jcondition!=null){
//                    System.out.println(jcondition.toString());
//                }
//            }
//        }
//        String s = "1";
//    }
    @Test
    public void selectBatchByDefSql2(){
        String sql = " vsrc_mark in ( '1001E410000000003T1Y','1001E410000000003T20') " ;
        String sql2 =
                "select * from cpu_biprice where vsrc_mark in ( '1001E410000000003T1Y','1001E410000000003T20')";
        String sql3 = " select * from cpu_biprice where jsupply is not null and (jsupply->>'aaa')::int>1 ";
        String sql4 = "  select * from cpu_biprice a ,cpu_biprice_0420 b where a.vtenant_id = b.vtenant_id ";
        List<BiPriceEntity> list2 = iBiPriceService.selectBatchByDefSql(sql4);
        String s = "1";
    }
    @Test
    public void updateByDefSqlTest(){
        String sql3 = "     insert into cpu_biprice (jsupply) values('{\"wa\": 6,\"cw\": 7}'::json) ";
        int num = iBiPriceService.updateByDefSql(sql3);
        String s = "1";
    }
//
//    @Test
//    public void testInsertBiPrice(){
//        BiPriceEntity biPriceEntity = new BiPriceEntity();
//        JSONObject jpurchase = new JSONObject();
//        jpurchase.put("aaa",7);
//        jpurchase.put("bbb",8);
//
//
//        JSONObject jsupply = new JSONObject();
//        jsupply.put("aaa",7);
//        jsupply.put("bbb",8);
//
//
//        JSONObject jmaterial = new JSONObject();
//        jmaterial.put("aaa",7);
//        jmaterial.put("bbb",8);
//
//
//        JSONObject jprice = new JSONObject();
//        jprice.put("aaa",7);
//        jprice.put("bbb",8);
//
//
//        JSONObject jcondition = new JSONObject();
//        jcondition.put("aaa",7);
//        jcondition.put("bbb",8);
//
//
////        biPriceEntity.setJsupply("{'aa': 6, 'cc': 7}");
//        biPriceEntity.setJsupply(jsupply);
//        biPriceEntity.setJpurchase(jpurchase);
//        biPriceEntity.setJmaterial(jmaterial);
//        biPriceEntity.setJprice(jprice);
//        biPriceEntity.setJcondition(jcondition);
//
//        iBiPriceService.insert(biPriceEntity);
//    }
//    @Test
//    public void testInsertUsersBatch(){
//        List<BiPriceEntity> usersList = new ArrayList<BiPriceEntity>();
//        BiPriceEntity biPriceEntity = null;
//        for(int i =0;i<5;i++){
//            biPriceEntity = new BiPriceEntity();
//            biPriceEntity.setVmaterial("dsfsf");
//            usersList.add(biPriceEntity);
//        }
//        iBiPriceService.insertBatch(usersList);
//    }
//    @Test
//    public void insertBatchTempTable(){
//        List<BiPriceEntity> usersList = new ArrayList<BiPriceEntity>();
//        BiPriceEntity biPriceEntity = null;
//        for(int i =0;i<5;i++){
//            biPriceEntity = new BiPriceEntity();
//            biPriceEntity.setVmaterial("temptabledata");
//            usersList.add(biPriceEntity);
//        }
//        iBiPriceService.insertBatchTempTable(usersList);
//    }

//    @Test
//    public void testString(){
//        long l = System.currentTimeMillis();
//        //new日期对象
//        Date date = new Date(l);
//        //转换提日期输出格式
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        String strDate = dateFormat.format(date);
//        System.out.println("----------------------------------"+strDate);
//    }
    @Test
    public void selectAvgMinPirce( ) {
        List<BiPriceEntity> biPriceEntityList = new ArrayList<BiPriceEntity>();
        BiPriceEntity biPriceEntity2 = new BiPriceEntity();
        biPriceEntity2.setVpurchaseEnterpriseId("30");
        biPriceEntity2.setVmaterialId("636051");
//        biPriceEntity2.setVsupplyEnterpriseId("637");
        biPriceEntityList.add(biPriceEntity2);
        BiPriceEntity biPriceEntity1 = iBiPriceService.selectAvgMinPirce(biPriceEntityList);
        String s = "d";
    }

    @Test
    public void selectLastPrice(  ){
        List<BiPriceEntity> biPriceEntityList = new ArrayList<BiPriceEntity>();
        BiPriceEntity biPriceEntity2 = new BiPriceEntity();
        biPriceEntity2.setVpurchaseEnterpriseId("30");
        biPriceEntity2.setVmaterialId("636051");
        biPriceEntity2.setVsupplyEnterpriseId("637");
        biPriceEntityList.add(biPriceEntity2);
        BiPriceEntity biPriceEntity1 = iBiPriceService.selectLastPrice(biPriceEntityList);
        String s = "d";
    }




//    @Test
//    public void testGetPrice(){
//        String json ="[{\"id\":\"1430\",\"supplierId\":\"1001E410000000003YYJ\",\"materialId\":\"1001E410000000003NJY\",\"purchaserId\":\"30\"},{\"id\":\"1431\",\"supplierId\":\"1001E410000000003YYJ\",\"materialId\":\"1001E410000000003NJY\",\"purchaserId\":\"30\"}]";
//        iBiPriceService.getPrice(json);
//    }











//    @Resource
//    private IUsersService usersService;
//
//    @Test
//    public void testGetMerchant() {
//        Users users = usersService.getUserById(10);
//        if(users!=null){
//            System.out.println(users.getUserId());
//        }else{
//            System.out.println("没有查找到数据！");
//        }
//    }
//    @Test
//    public void testInsert(){
//        Users users1 = new Users();
//        users1.setUserId("users");
//        users1.setLeaderId("user001");
//        users1.setUserName("user2_name");
//        users1.setUserType(2);
//        users1.setContext("{'name1':'test1','name2':'test2'}");
//        usersService.insert(users1);
//    }
//
//    @Test
//    public void testInsertUsersBatch(){
//        List<Users> usersList = new ArrayList<Users>();
//        Users users1 = null;
//        for(int i =0;i<5;i++){
//            users1 = new Users();
//            users1.setUserId("users_"+i);
//            users1.setLeaderId("user001");
//            users1.setUserName("user_"+i+"_name");
//            users1.setUserType(2);
//            users1.setContext("{\"name1\":\"test1\",\"name2\":\"test2\"}");
//            usersList.add(users1);
//        }
//        usersService.insertUsersBatch(usersList);
//        usersService.updateString2Json();
//    }
//
//    @Test
//    public void updateString2Json(){
//        usersService.updateString2Json();
//    }
}
