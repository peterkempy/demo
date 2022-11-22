package pk.demo.containers.demo.service;

import org.json.simple.JSONObject;

public class HostnameService {

    public static String getMachineDetails() {
        JSONObject machineDetails = new JSONObject();
        machineDetails.put("ipAddress", getIpAddress());
        return machineDetails.toJSONString();
    }

    public static String getIpAddress() {
        try {
            java.util.Enumeration<java.net.NetworkInterface> interfaces = java.net.NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                java.net.NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                java.util.Enumeration<java.net.InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    java.net.InetAddress addr = addresses.nextElement();

                    final String ip = addr.getHostAddress();
                    if(java.net.Inet4Address.class == addr.getClass()) return ip;
                }
            }
        } catch (java.net.SocketException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}