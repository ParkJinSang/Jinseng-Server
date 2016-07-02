package connection;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import core.http.HttpServiceRouter;
import core.http.IWebServiceLogic;
import core.tcp.IServiceLogic;

public class ConnectionManager {
	/***
	 * 
	 * 	Manage the connection units that central server had.
	 * Create, edit, select and delete a unit using this singleton class.
	 * 
	 */
	
	private static ConnectionManager instance = null;

	private Map<String, ConnectionUnit> connectionMap = new HashMap<String, ConnectionUnit>();		//It can only be accessed by select query to this class.
	
	private ConnectionManager(){
		
	}
	public static ConnectionManager GetInstance(){
		if(instance == null){
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	private String GenerateConnectionId(){
		String conUUID = "";
		while(true){
			conUUID = UUID.randomUUID().toString();
			if(!connectionMap.containsKey(conUUID))
				break;
		}
		return conUUID;
	}
	private void RegisterConId(String uuid, ConnectionUnit newCon){
		connectionMap.put(uuid, newCon);
	}

	/***
	 * Create normal connection unit based on TCP (connection based)
	 * @param endPoint
	 * @param businessLogic
	 * @return
	 */
	public ConnectionUnit CreateNewUnit(Socket endPoint, IServiceLogic businessLogic){
		
		String newConId = GenerateConnectionId();
		ConnectionUnit newOne = new ConnectionUnit(newConId, endPoint, businessLogic);
		RegisterConId(newConId, newOne);
		
		return newOne;
	}
	
	public ConnectionUnit CreateNewUnit(Socket endPoint, IServiceLogic businessLogic, boolean loop){
		
		String newConId = GenerateConnectionId();
		ConnectionUnit newOne = new ConnectionUnit(newConId, endPoint, businessLogic, loop);
		RegisterConId(newConId, newOne);
		
		return newOne;
	}
	public ConnectionUnit CreateNewUnit(Socket endPoint, IServiceLogic businessLogic, boolean loop, int term){
		
		String newConId = GenerateConnectionId();
		ConnectionUnit newOne = new ConnectionUnit(newConId, endPoint, businessLogic, loop, term);
		RegisterConId(newConId, newOne);
		
		return newOne;
	}
	
	/***
	 * Create web connection unit based on HTTP (connectless)
	 * @param endPoint
	 * @param businessLogic
	 * @return
	 */
	public WebConnectionUnit CreateNewWebUnit(Socket endPoint, IWebServiceLogic businessLogic){
		
		String newConId = GenerateConnectionId();
		WebConnectionUnit newOne = new WebConnectionUnit(newConId, endPoint, businessLogic);
		
		return newOne;
	}
	
	public WebConnectionUnit CreateNewWebUnit(Socket endPoint, HttpServiceRouter router){
		
		String newConId = GenerateConnectionId();
		WebConnectionUnit newOne = new WebConnectionUnit(newConId, endPoint, router);
		
		return newOne;
	}
	
	/***
	 * Get connection map that has all information of connected objects.
	 * @return
	 */
	public Map<String, ConnectionUnit> ReadConnectionMap(){
		return connectionMap;
	}
	
	/***
	 * Update connection map by key and new object.
	 * @param key
	 * @param newObject
	 */
	public void UpdateConnectionMap(String key, ConnectionUnit newObject){
		connectionMap.put(key, newObject);
	}
	
	/***
	 * Delete connection unit from connection map.
	 * @param connectionId
	 */
	public void DeleteUnit(String connectionId){
		
		try {
			//Socket Close.
			connectionMap.get(connectionId).getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectionMap.remove(connectionId);
	}

}
