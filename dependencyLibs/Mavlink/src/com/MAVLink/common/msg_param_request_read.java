// MESSAGE PARAM_REQUEST_READ PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Request to read the onboard parameter with the param_id string id. Onboard parameters are stored as key[const char*] -> value[float]. This allows to send a parameter to any other component (such as the GCS) without the need of previous knowledge of possible parameter names. Thus the same GCS can store different parameters for different autopilots. See also http://qgroundcontrol.org/parameter_interface for a full documentation of QGroundControl and IMU code.
*/
public class msg_param_request_read extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_PARAM_REQUEST_READ = 20;
    public static final int MAVLINK_MSG_LENGTH = 20;
    private static final long serialVersionUID = MAVLINK_MSG_ID_PARAM_REQUEST_READ;


     	
    /**
    * Parameter index. Send -1 to use the param ID field as identifier (else the param id will be ignored)
    */
    public short param_index;
     	
    /**
    * System ID
    */
    public byte target_system;
     	
    /**
    * Component ID
    */
    public byte target_component;
     	
    /**
    * Onboard parameter id, terminated by NULL if the length is less than 16 human-readable chars and WITHOUT null termination (NULL) byte if the length is exactly 16 chars - applications have to provide 16+1 bytes storage if the ID is stored as string
    */
    public byte param_id[] = new byte[16];
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_PARAM_REQUEST_READ;
        		packet.payload.putShort(param_index);
        		packet.payload.putByte(target_system);
        		packet.payload.putByte(target_component);
        		
        for (int i = 0; i < param_id.length; i++) {
            packet.payload.putByte(param_id[i]);
        }
                    
        
        return packet;
    }

    /**
    * Decode a param_request_read message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        	    
        this.param_index = payload.getShort();
        	    
        this.target_system = payload.getByte();
        	    
        this.target_component = payload.getByte();
        	    
         
        for (int i = 0; i < this.param_id.length; i++) {
            this.param_id[i] = payload.getByte();
        }
                
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_param_request_read(){
        msgid = MAVLINK_MSG_ID_PARAM_REQUEST_READ;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_param_request_read(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_PARAM_REQUEST_READ;
        unpack(mavLinkPacket.payload);        
    }

           
    /**
    * Sets the buffer of this message with a string, adds the necessary padding
    */
    public void setParam_Id(String str) {
        int len = Math.min(str.length(), 16);
        for (int i=0; i<len; i++) {
            param_id[i] = (byte) str.charAt(i);
        }

        for (int i=len; i<16; i++) {			// padding for the rest of the buffer
            param_id[i] = 0;
        }
    }

    /**
    * Gets the message, formated as a string
    */
    public String getParam_Id() {
        String result = "";
        for (int i = 0; i < 16; i++) {
            if (param_id[i] != 0)
                result = result + (char) param_id[i];
            else
                break;
        }
        return result;

    }
                         
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_PARAM_REQUEST_READ -"+" param_index:"+param_index+" target_system:"+target_system+" target_component:"+target_component+" param_id:"+param_id+"";
    }
}
        