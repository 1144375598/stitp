# stitp
njupt_stitp_server

��ע��Localhost:8080��ʱ�����Ϊ����ip

��¼�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=?&user.password=?  
���ݸ�ʽ����json������username��password�����ֶ�,������username=1��password=2�����ַΪ
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=��1��&user.password=��2��
���������ص�״̬���룺
				*result_code
				 * 0: ��½�ɹ���
				 *1����½ʧ�ܣ��û������������
				 * 2����½ʧ�ܣ��û��������ڣ�
	



ע��ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/register? user.username=?&user.password=?
���ݸ�ʽ����json������username��password 
���������ص�״̬���룺
*   result_code: 
					* 0 ע��ɹ�
		 			* 1  �û����Ѵ���

�ϴ�app��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/appInfo?app.user.username=?&app.appName=?&app.appUseTime=?
username��passwordҪ��˫���Ŵ���

�ϴ��켣��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?
usernameҪ��˫���Ŵ��䣬longitudeΪ���ȣ�latitudeΪγ��

