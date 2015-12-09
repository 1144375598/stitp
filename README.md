备注：Localhost:8080到时会更换为公网ip

登录接口：
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=?&user.password=?  
例如若username=1，password=2，则地址为
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=1&user.password=2
服务器返回的状态代码：
				*result_code
				 * 0: 登陆成功！
				 *1：登陆失败，用户名或密码错误！
				 * 2：登陆失败，用户名不存在！
	返回json数据，类型为map<String,Object>,例如登录成功，则返回值为{result_code,0}



注册接口：
http://localhost:8080/NJUPT_STITP_Server/user/register? user.username=?&user.password=?
数据格式采用json，包含username和password 
服务器返回的状态代码：
*   result_code: 
					* 0 注册成功
		 			* 1  用户名已存在

返回json数据，类型为map<String,Object>,例如注册成功，则返回值为{result_code,0}
上传app信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/appInfo?app.user.username=?&app.appName=?&app.appUseTime=?
上传轨迹信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?& track.addTime=yyyy-MM-dd HH:mm:ss
longitude为经度，latitude为纬度,addTime为经纬度时间(如2004-5-31 23:59:59)




上传使用时间控制信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/useTimeControlInfo?useTimeControl.user.username=?&useTimeControl.start=?& useTimeControl.end=?
username为孩子的名字的字符串，start和end为0-24的小数，代表24小时，如家长规定孩子在9:30-10:15可以玩手机，则start=9.5,end为10.25

上传地理围栏信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/geoFencingInfo?geoFencing.user.username=?&geoFencing.longtitude=?&geoFencing.latitude=?&geoFencing.distance=?


上传护眼时间接口：
http://localhost:8080/NJUPT_STITP_Server/user/continueUseTime?user.username=?&user. timeOfContinuousUse=?
Username为孩子的用户名，timeOfContinuousUse为连续使用多长时间后护眼，时间单位为分钟
家长每次设置了孩子的护眼时间后，服务器会向孩子客户端推送消息提示护眼时间更新，推送的字符串为：ContinueUseTime updated

下载护眼时间接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/continueUseInfo?user.username=?
Username为孩子的用户名，即护眼信息所属的孩子
服务器状态代码：/*
		 * result_code
		 * 0 查询成功
		 * 1 未设置护眼时间
		 */
返回json数据，类型为map<String,Object>，如查询成功，则返回<”result_code”:”0”, ”result”:list<ContinueUseTimeDto >>,若失败返回<”result_code”:”1”>
ContinueUseTimeDto定义：
public class ContinueUseTimeDto {
	private String username;
	private int continueUseTime;
}
下载轨迹信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/trackInfo?user.username=?&dateString=yyyy-MM-dd
/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无轨迹信息
		 */
返回json数据，类型为map<String,Object>，如查询成功，则返回<”result_code”:”0”, ”result”:list<TrackDto> >,若失败返回<”result_code”:”1”>或<”result_code”:”2”>
TrackDto定义: public class TrackDto {
					private String username;
					private Date addTime;
					private double longitude;
					private double latitude;}

下载APP信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/appInfo?user.username=?&dateString=yyyy-MM-dd
/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无app信息
		 */


返回json数据，类型为map<String,Object>，如查询成功，返回<”result_code”:”0”, ”result”:list<APPDto> >,若失败返回<”result_code”:”1”>或<”result_code”:”2”>
APPDto定义：public class APPDto {
					private String username;
					private int appUseTime;
					private String appName;
					private Date date;}


下载UseTimeControl信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/useTimeControlInfo?user.username=?
	服务器状态代码：
/*
		 * result_code
		 * 0 查询成功
		 * 1 无使用时间控制信息
		 */

返回json数据，类型为map<String,Object>，如查询成功，返回<”result_code”:”0”, ”result”:list<UseTimeControlDto> >,若失败返回<”result_code”:”1”>

UseTimeControlDto定义：public class UseTimeControlDto {
								private String username;
								private double start;
								private double end;}

下载地理围栏信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/geoFencingInfo?user.username=?
	服务器状态代码：
/*
		 * result_code
		 * 0 查询成功
		 * 1 无地理围栏信息
		 */
返回json数据，类型为map<String,Object>，如查询成功，返回<”result_code”:”0”, ”result”:list<GeoFencingDto> >,若失败返回<”result_code”:”1”>

GeoFencingDto定义：  public class GeoFencingDto {
						private String username;
						private double longtitude;
						private double latitude;
						private double distance;}


删除孩子接口：
http://localhost:8080/NJUPT_STITP_Server/user/deleteChild?user.username=?&friendName=?
服务器状态代码： /*
		 			*   result_code: 
		 			* 0 删除成功
		 			* 1  不可删除家长
		 			 */

返回json数据，类型为Map<String, Object>，如删除成功，返回{“result_code”,”0”},失败返回{“result_code”,”1”}

上传uid和cid接口：
http://localhost:8080/NJUPT_STITP_Server/user/ uidAndCid?user.username=?&user.cid=?

加好友接口：
http://localhost:8080/NJUPT_STITP_Server/user/ addFriend?user.username=?&friendName=?&message=?&relationship=?
/*
		 * result_code
		 * 0 验证消息发送成功
		 * 1 消息发送失败
		 */
Relationship取值为child或parents
若A要添加B为孩子,向B发送验证消息“加好友”，则A向服务器上传的信息为
http://localhost:8080/NJUPT_STITP_Server/user/ addFriend?user.username=”A”&friendName=”B“&message=”加好友“&relationship=”child”

向好友发送的验证消息格式为：
String mes = "username =" + username + "," + "message =" + message + "," +"relationship =" + relationship;

返回json数据，类型为Map<String, Object>，如删除成功，返回{“result_code”,”0”},失败返回{“result_code”,”1”}




上传添加好友结果接口：
http://localhost:8080/NJUPT_STITP_Server/user/ addFriendResult?resultCode=?&user.username=?&friendName=?&relationship=?

Resultcode为0表示同意，1表示不同意
Relationship取值为child或parents

若A要添加B为孩子，B同意，则B向服务器返回的信息为
http://localhost:8080/NJUPT_STITP_Server/user/ addFriendResult?resultCode=0&user.username=”A“&friendName=”B”&relationship=”child“
同时服务器向A返回”friendName =B,relationship =child,addFriendResult =1”
向A返回的字符串格式为：
String result = "friendName =" + friendName + "," +"relationship =" + relationship + "," +"addFriendResult ="+resultCode;


向好友发送信息接口：
http://localhost:8080/NJUPT_STITP_Server/user/sendMessage?user.username=&friendName=&message=
/*
		 * result_code
		 * 0 消息发送成功
		 * 1 消息发送失败
		 */
返回json数据，类型为Map<String, Object>，如删除成功，返回{“result_code”,”0”},失败返回{“result_code”,”1”}

向好友发送的消息格式为：
String mes = "username =" + user.getUsername() + "," + "message =" + message +”,”+"date =" + new Date().toString();

获取验证问题接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/ validationQuestionInfo?user.username=?
返回json数据，类型为map<String,Object>，如查询成功，返回<”result_code”:”0”, ”result”:list<ValidationQuestionDto> >,若失败返回<”result_code”:”1”>
ValidationQuestionDto定义：public class ValidationQuestionDto {
	private String question;
	private String answer;
	private String username;}


修改密码接口：
http://localhost:8080/NJUPT_STITP_Server/user/resetPassword?user.username=?&user.password=?  
一键锁屏：
http://localhost:8080/NJUPT_STITP_Server/user/lockScreen?user.username=?
上传的信息为要锁屏的孩子的用户名，向孩子端发送的消息为字符串”lock screen”

向家长通知孩子超出地理围栏：
当客户端上传经纬度信息时，服务器进行检测，如果检测出超出范围了，就向所有的家长账号发送通知，通知为字符串，格式为username+”, out of range”。
Username为超出范围的孩子的名字



获取孩子信息：
http://localhost:8080/NJUPT_STITP_Server/user/getChild?user.username=?
user.username为家长的用户名。
服务器返回的result_code: 1:该用户有孩子
						1：该用户无孩子
返回json数据，类型为map<String,Object>，如查询成功，返回<”result_code”:”0”, ”result”:list<UserDto> >,若失败返回<”result_code”:”1”>.若孩子的timeOfContinuousUse，timeOfContinuousListen，musicVolume，cid这四个字段有未设置的，则服务器返回该字段为空值。
返回的json数据实例：无孩子：{"result_code":1}
有孩子：
{"result_code":0,"result":[{"username":"2","timeOfContinuousUse":0,"timeOfContinuousListen":0,"musicVolume":0,"cid":"0"},{"username":"3","timeOfContinuousUse":0,"timeOfContinuousListen":0,"musicVolume":0,"cid":"0"}]}

UserDto定义：public class UserDto {
						private String username;
						private int timeOfContinuousUse;
						private int timeOfContinuousListen;
						private int musicVolume;
						private String cid;}
