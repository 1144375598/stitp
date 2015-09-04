
备注：Localhost:8080到时会更换为公网ip

登录接口：
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=?&user.password=?  
数据格式采用json，包含username和password两个字段,例如若username=1，password=2，则地址为
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=”1”&user.password=”2”
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
username和password要加双引号传输

上传轨迹信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?
username要加双引号传输，longitude为经度，latitude为纬度,不加引号




上传使用时间控制信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/useTimeControlInfo?useTimeControl.user.username=?&useTimeControl.start=?& useTimeControl.end=?
username为孩子的名字的字符串，加引号传输，start和end为0-24的小数，代表24小时，如家长规定孩子在9:30-10:15可以玩手机，则start=9.5,end为10.25

上传地理围栏信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/ GeoFencingInfo?geoFencing.user.username=?&geofencing.longitude=?&geofencing.latitude=?&geofencing.distance=?

下载轨迹信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/trackInfo?user.username=?&dateString=yyyy-MM-dd
datastring的值不用加双引号

/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无轨迹信息
		 */
返回json数据，类型为map<Integer,Object>，如查询成功，则Integer为0，object为List<TrackDto>,若传递的时间错误，如格式不对的话，则Integer为1，object为字符串"wrong parameters"，若当日无轨迹信息，则Integer为2，object为字符串"no result"
TrackDto定义: public class TrackDto {
					private String username;
					private Date addTime;
					private double longitude;
					private double latitude;}

下载APP信息接口：
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/appInfo?user.username=?&dateString=yyyy-MM-dd
datastring的值不用加双引号
/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无app信息
		 */


返回json数据，类型为map<Integer,Object>，如查询成功，则Integer为0，object为List<APPDto>,若传递的时间错误，如格式不对的话，则Integer为1，object为字符串"wrong parameters"，
若当日无app信息，则Integer为2，object为字符串"no result"，
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

返回json数据，类型为map<Integer,Object>，如查询成功，则Integer为0，object为List< UseTimeControlDto >,若无时间控制信息，则Integer为1，object为字符串" no result"

UseTimeControlDto定义：public class UseTimeControlDto {
								private String username;
								private double start;
								private double end;}
删除好友接口：
http://localhost:8080/NJUPT_STITP_Server/user/deleteFriend?user.username=?&friendName=?
服务器状态代码： /*
		 			*   result_code: 
		 			* 0 删除成功
		 			* 1  不可删除家长
		 			 */

返回json数据，类型为Map<String, Object>，如删除成功，返回{“result_code”,”0”},失败返回{“result_code”,”1”}
