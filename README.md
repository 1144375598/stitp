# stitp
njupt_stitp_server

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
	



注册接口：
http://localhost:8080/NJUPT_STITP_Server/user/register? user.username=?&user.password=?
数据格式采用json，包含username和password 
服务器返回的状态代码：
*   result_code: 
					* 0 注册成功
		 			* 1  用户名已存在

上传app信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/appInfo?app.user.username=?&app.appName=?&app.appUseTime=?
username和password要加双引号传输

上传轨迹信息接口：
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?
username要加双引号传输，longitude为经度，latitude为纬度

