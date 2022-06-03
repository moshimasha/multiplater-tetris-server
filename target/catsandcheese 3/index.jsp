<%@ page import ="java.io.*" %>
<!DOCTYPE html>
<html>
	<head>
		<link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
		<style type="text/css">
			.text-bar {
				text-align: center;
				font-family: Lato;
			}
			.header {
				margin: 20px 20px 20px 20px;
				font-size: 50px;
			}
			.main-content {
				text-align: center;
			}
			.main-content>div {
				display: inline-block;
				vertical-align: middle;
			}
			.footer {
				margin: 20px 20px 20px 20px;
				font-size: 14px;
			}
			.cheese {
				margin: 30px 30px 30px 30px;
			}
			.cheese>img {
				width: 200px;
			}
		</style>
	</head>
<body>
	<div class="header text-bar">CATS &amp; CHEESE</div>
	<div class="main-content">
		<div class="cheese">
			<img src="img/cheese1.jpg">
		</div>
		<div class="cat">
			<img src="img/findus.jpg" >
		</div>
		<div class="cheese">
			<img src="img/cheese2.jpg">
		</div>
	</div>
	<div class="footer text-bar">(c) 2019 Masha</div>
	<div class="counter text-bar">
<%!
	final String COUNTER_KEY = "kCounter";
	final String COUNTER_PATH = "WEB-INF/counter.my";
	
	public Integer readCounter(String path) {
		
		int retVal = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);
			retVal = dis.readInt();
			dis.close();
		} catch (IOException e) {
			//
		} 
		return new Integer(retVal);
	}
	
	public void writeCounter(Integer c, String path) {
		
		try {
			FileOutputStream fos = new FileOutputStream(path);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(c);
			dos.close();
		} catch (IOException e) {
			System.out.println("error");
		}
	}
%>
<%
	Integer c = (Integer)application.getAttribute(COUNTER_KEY);
	
	if (c == null) {
		c = readCounter(application.getRealPath(COUNTER_PATH));
	} 
	c++;	
	application.setAttribute(COUNTER_KEY, c);
	writeCounter(c, application.getRealPath(COUNTER_PATH));
	out.print(c);
%>	
	</div>
</body>
</html>

