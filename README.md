
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
	����json���ݣ�����Ϊmap<String,Object>,�����¼�ɹ����򷵻�ֵΪ{result_code,0}



ע��ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/register? user.username=?&user.password=?
���ݸ�ʽ����json������username��password 
���������ص�״̬���룺
*   result_code: 
					* 0 ע��ɹ�
		 			* 1  �û����Ѵ���

����json���ݣ�����Ϊmap<String,Object>,����ע��ɹ����򷵻�ֵΪ{result_code,0}
�ϴ�app��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/appInfo?app.user.username=?&app.appName=?&app.appUseTime=?
username��passwordҪ��˫���Ŵ���

�ϴ��켣��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?
usernameҪ��˫���Ŵ��䣬longitudeΪ���ȣ�latitudeΪγ��,��������




�ϴ�ʹ��ʱ�������Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/useTimeControlInfo?useTimeControl.user.username=?&useTimeControl.start=?& useTimeControl.end=?
usernameΪ���ӵ����ֵ��ַ����������Ŵ��䣬start��endΪ0-24��С��������24Сʱ����ҳ��涨������9:30-10:15�������ֻ�����start=9.5,endΪ10.25

�ϴ�����Χ����Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/ GeoFencingInfo?geoFencing.user.username=?&geofencing.longitude=?&geofencing.latitude=?&geofencing.distance=?

���ع켣��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/trackInfo?user.username=?&dateString=yyyy-MM-dd
datastring��ֵ���ü�˫����

/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 ��������
		 * 2 �޹켣��Ϣ
		 */
����json���ݣ�����Ϊmap<Integer,Object>�����ѯ�ɹ�����IntegerΪ0��objectΪList<TrackDto>,�����ݵ�ʱ��������ʽ���ԵĻ�����IntegerΪ1��objectΪ�ַ���"wrong parameters"���������޹켣��Ϣ����IntegerΪ2��objectΪ�ַ���"no result"
TrackDto����: public class TrackDto {
					private String username;
					private Date addTime;
					private double longitude;
					private double latitude;}

����APP��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/appInfo?user.username=?&dateString=yyyy-MM-dd
datastring��ֵ���ü�˫����
/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 ��������
		 * 2 ��app��Ϣ
		 */


����json���ݣ�����Ϊmap<Integer,Object>�����ѯ�ɹ�����IntegerΪ0��objectΪList<APPDto>,�����ݵ�ʱ��������ʽ���ԵĻ�����IntegerΪ1��objectΪ�ַ���"wrong parameters"��
��������app��Ϣ����IntegerΪ2��objectΪ�ַ���"no result"��
APPDto���壺public class APPDto {
					private String username;
					private int appUseTime;
					private String appName;
					private Date date;}


����UseTimeControl��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/useTimeControlInfo?user.username=?
	������״̬���룺
/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 ��ʹ��ʱ�������Ϣ
		 */

����json���ݣ�����Ϊmap<Integer,Object>�����ѯ�ɹ�����IntegerΪ0��objectΪList< UseTimeControlDto >,����ʱ�������Ϣ����IntegerΪ1��objectΪ�ַ���" no result"

UseTimeControlDto���壺public class UseTimeControlDto {
								private String username;
								private double start;
								private double end;}
ɾ�����ѽӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/deleteFriend?user.username=?&friendName=?
������״̬���룺 /*
		 			*   result_code: 
		 			* 0 ɾ���ɹ�
		 			* 1  ����ɾ���ҳ�
		 			 */

����json���ݣ�����ΪMap<String, Object>����ɾ���ɹ�������{��result_code��,��0��},ʧ�ܷ���{��result_code��,��1��}
