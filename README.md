��ע��Localhost:8080��ʱ�����Ϊ����ip

��¼�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=?&user.password=?  
������username=1��password=2�����ַΪ
http://localhost:8080/NJUPT_STITP_Server/user/login?user.username=1&user.password=2
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
�ϴ��켣��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/trackInfo?track.user.username=?&track.longitude=?&track.latitude=?& track.addTime=yyyy-MM-dd HH:mm:ss
longitudeΪ���ȣ�latitudeΪγ��,addTimeΪ��γ��ʱ��(��2004-5-31 23:59:59)




�ϴ�ʹ��ʱ�������Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/useTimeControlInfo?useTimeControl.user.username=?&useTimeControl.start=?& useTimeControl.end=?
usernameΪ���ӵ����ֵ��ַ�����start��endΪ0-24��С��������24Сʱ����ҳ��涨������9:30-10:15�������ֻ�����start=9.5,endΪ10.25

�ϴ�����Χ����Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/uploadInfo/geoFencingInfo?geoFencing.user.username=?&geoFencing.longtitude=?&geoFencing.latitude=?&geoFencing.distance=?


�ϴ�����ʱ��ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/continueUseTime?user.username=?&user. timeOfContinuousUse=?
UsernameΪ���ӵ��û�����timeOfContinuousUseΪ����ʹ�ö೤ʱ����ۣ�ʱ�䵥λΪ����
�ҳ�ÿ�������˺��ӵĻ���ʱ��󣬷����������ӿͻ���������Ϣ��ʾ����ʱ����£����͵��ַ���Ϊ��ContinueUseTime updated

���ػ���ʱ��ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/continueUseInfo?user.username=?
UsernameΪ���ӵ��û�������������Ϣ�����ĺ���
������״̬���룺/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 δ���û���ʱ��
		 */
����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ����򷵻�<��result_code��:��0��, ��result��:list<ContinueUseTimeDto >>,��ʧ�ܷ���<��result_code��:��1��>
ContinueUseTimeDto���壺
public class ContinueUseTimeDto {
	private String username;
	private int continueUseTime;
}
���ع켣��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/trackInfo?user.username=?&dateString=yyyy-MM-dd
/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 ��������
		 * 2 �޹켣��Ϣ
		 */
����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ����򷵻�<��result_code��:��0��, ��result��:list<TrackDto> >,��ʧ�ܷ���<��result_code��:��1��>��<��result_code��:��2��>
TrackDto����: public class TrackDto {
					private String username;
					private Date addTime;
					private double longitude;
					private double latitude;}

����APP��Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/appInfo?user.username=?&dateString=yyyy-MM-dd
/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 ��������
		 * 2 ��app��Ϣ
		 */


����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ�������<��result_code��:��0��, ��result��:list<APPDto> >,��ʧ�ܷ���<��result_code��:��1��>��<��result_code��:��2��>
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

����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ�������<��result_code��:��0��, ��result��:list<UseTimeControlDto> >,��ʧ�ܷ���<��result_code��:��1��>

UseTimeControlDto���壺public class UseTimeControlDto {
								private String username;
								private double start;
								private double end;}

���ص���Χ����Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/geoFencingInfo?user.username=?
	������״̬���룺
/*
		 * result_code
		 * 0 ��ѯ�ɹ�
		 * 1 �޵���Χ����Ϣ
		 */
����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ�������<��result_code��:��0��, ��result��:list<GeoFencingDto> >,��ʧ�ܷ���<��result_code��:��1��>

GeoFencingDto���壺  public class GeoFencingDto {
						private String username;
						private double longtitude;
						private double latitude;
						private double distance;}


ɾ�����ӽӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/deleteChild?user.username=?&friendName=?
������״̬���룺 /*
		 			*   result_code: 
		 			* 0 ɾ���ɹ�
		 			* 1  ����ɾ���ҳ�
		 			 */

����json���ݣ�����ΪMap<String, Object>����ɾ���ɹ�������{��result_code��,��0��},ʧ�ܷ���{��result_code��,��1��}

�ϴ�uid��cid�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/ uidAndCid?user.username=?&user.cid=?

�Ӻ��ѽӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/ addFriend?user.username=?&friendName=?&message=?&relationship=?
/*
		 * result_code
		 * 0 ��֤��Ϣ���ͳɹ�
		 * 1 ��Ϣ����ʧ��
		 */
RelationshipȡֵΪchild��parents
��AҪ���BΪ����,��B������֤��Ϣ���Ӻ��ѡ�����A��������ϴ�����ϢΪ
http://localhost:8080/NJUPT_STITP_Server/user/ addFriend?user.username=��A��&friendName=��B��&message=���Ӻ��ѡ�&relationship=��child��

����ѷ��͵���֤��Ϣ��ʽΪ��
String mes = "username =" + username + "," + "message =" + message + "," +"relationship =" + relationship;

����json���ݣ�����ΪMap<String, Object>����ɾ���ɹ�������{��result_code��,��0��},ʧ�ܷ���{��result_code��,��1��}




�ϴ���Ӻ��ѽ���ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/ addFriendResult?resultCode=?&user.username=?&friendName=?&relationship=?

ResultcodeΪ0��ʾͬ�⣬1��ʾ��ͬ��
RelationshipȡֵΪchild��parents

��AҪ���BΪ���ӣ�Bͬ�⣬��B����������ص���ϢΪ
http://localhost:8080/NJUPT_STITP_Server/user/ addFriendResult?resultCode=0&user.username=��A��&friendName=��B��&relationship=��child��
ͬʱ��������A���ء�friendName =B,relationship =child,addFriendResult =1��
��A���ص��ַ�����ʽΪ��
String result = "friendName =" + friendName + "," +"relationship =" + relationship + "," +"addFriendResult ="+resultCode;


����ѷ�����Ϣ�ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/sendMessage?user.username=&friendName=&message=
/*
		 * result_code
		 * 0 ��Ϣ���ͳɹ�
		 * 1 ��Ϣ����ʧ��
		 */
����json���ݣ�����ΪMap<String, Object>����ɾ���ɹ�������{��result_code��,��0��},ʧ�ܷ���{��result_code��,��1��}

����ѷ��͵���Ϣ��ʽΪ��
String mes = "username =" + user.getUsername() + "," + "message =" + message +��,��+"date =" + new Date().toString();

��ȡ��֤����ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/downloadInfo/ validationQuestionInfo?user.username=?
����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ�������<��result_code��:��0��, ��result��:list<ValidationQuestionDto> >,��ʧ�ܷ���<��result_code��:��1��>
ValidationQuestionDto���壺public class ValidationQuestionDto {
	private String question;
	private String answer;
	private String username;}


�޸�����ӿڣ�
http://localhost:8080/NJUPT_STITP_Server/user/resetPassword?user.username=?&user.password=?  
һ��������
http://localhost:8080/NJUPT_STITP_Server/user/lockScreen?user.username=?
�ϴ�����ϢΪҪ�����ĺ��ӵ��û��������Ӷ˷��͵���ϢΪ�ַ�����lock screen��

��ҳ�֪ͨ���ӳ�������Χ����
���ͻ����ϴ���γ����Ϣʱ�����������м�⣬�������������Χ�ˣ��������еļҳ��˺ŷ���֪ͨ��֪ͨΪ�ַ�������ʽΪusername+��, out of range����
UsernameΪ������Χ�ĺ��ӵ�����



��ȡ������Ϣ��
http://localhost:8080/NJUPT_STITP_Server/user/getChild?user.username=?
user.usernameΪ�ҳ����û�����
���������ص�result_code: 1:���û��к���
						1�����û��޺���
����json���ݣ�����Ϊmap<String,Object>�����ѯ�ɹ�������<��result_code��:��0��, ��result��:list<UserDto> >,��ʧ�ܷ���<��result_code��:��1��>.�����ӵ�timeOfContinuousUse��timeOfContinuousListen��musicVolume��cid���ĸ��ֶ���δ���õģ�����������ظ��ֶ�Ϊ��ֵ��
���ص�json����ʵ�����޺��ӣ�{"result_code":1}
�к��ӣ�
{"result_code":0,"result":[{"username":"2","timeOfContinuousUse":0,"timeOfContinuousListen":0,"musicVolume":0,"cid":"0"},{"username":"3","timeOfContinuousUse":0,"timeOfContinuousListen":0,"musicVolume":0,"cid":"0"}]}

UserDto���壺public class UserDto {
						private String username;
						private int timeOfContinuousUse;
						private int timeOfContinuousListen;
						private int musicVolume;
						private String cid;}
