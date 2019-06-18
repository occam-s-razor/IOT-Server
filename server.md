# 概要说明文档

## 客户端

客户端通过发布"client_conversation"主题向服务器发送数据

客户端打开即连接服务器，并订阅主题：client_id+"_conversation"，服务器通过此主题来发送登录，修改密码，传感器等所有信息

### 登录

客户端登录连接服务器

```json
{
	"type": "login",
	"client_id": "a1324562312",
	"msg": {
		"username": "aaa",
		"password": "123456"
	}
}
```

服务器返回json数据

login success
```json
{
	"type": "login_return",
	"status": "ok",
	"gateway_id": "2016110201"
}
```

login failed
```json
{
	"type": "login_return",
	"status": "error"
}
```

### 修改密码

用户修改密码

```json
{
	"type": "modify",
	"client_id": "a1324562312",
	"msg": {
		"username": "aaa",
		"password": "123456",
		"newpassword": "234567"
	}
}
```

服务器返回数据

modify success
```json
{
	"type": "modify_return",
	"client_id": "a1324562312",
	"status": "ok"
}
```

modify failed
```json
{
	"type": "modify_return",
	"client_id": "a1324562312",
	"status": "error"
}
```

### 家居控制

数据为单个设备控制的json数据：

```json
{
	"type": "gateway_control",
	"client_id": "a1324562312",
	"fan": "20"
}
或
{
	"type": "gateway_control",
	"client_id": "a1324562312",
	"light0": "on"
}
或
{
	"type": "gateway_control",
	"client_id": "a1324562312",
	"light1": "off"
}
```

### 接收家居信息

数据格式(json)：
```json
{
	"type": "dashboard",
	"gateway_id": "2016110201",
	"sensors_value": {
		"time_stamp", "2019-06-18 10:00:49",
		"temperature": "26.3",
		"humidity": "60",
		"fan": "69",
		"beam": "56",
		"light1": "on",
		"light2": "off"
		
	}
}
```

## 家居网关

开机主动连接服务器，发布主题："gateway_conversation"，用于接收所有服务器发送给家庭网关的信息

### 订阅主题

订阅主题：gateway_id+"_conversation"，用于接收所有服务器发送给家庭网关的信息

例：(server-->gateway)
```json
{
	"type": "client_control",
	"fan": "20"
}
或
{
	"type": "client_control",
	"light1": "on"
}
或
{
	"type": "client_control",
	"light1": "off"
}
```

### 发送家居信息

数据格式(json)：
```json
{
	"type": "dashboard",
	"gateway_id": "2016110201",
	"sensors_value": {
		"temperature": "26.3",
		"humidity": "60",
		"fan": "69",
		"beam": "56",
		"light1": "on",
		"light2": "off"
		
	}
}
```

## 服务器

服务器订阅主题："client_conversation"，用于接收所有客户端发送的数据

服务器订阅主题："gateway_conversation"，用于接收所有家庭网关发送的数据

### 客户端-->服务器

登录，修改密码等

控制家居

### 家庭网关-->服务器

家居信息

### 数据库

table: gateway_user
主键：gateway_id
|gateway_id|username|
|:-:|:-:|:-:|
|12346asd435|3as4df321z3|aaa|

table: user_info
主键：username
password=MD5(real_pwd)
|username|password|
|:-:|:-:|:-:|:-:|
|aaa|cshjdbvfmxzbv|

table: client_user
主键：client_id
|client_id|username|
|:-:|:-:|
|12346513213|aaa|
|jhSDGFjhxzd|aaa|

table: gateway_info
主键：time_stamp
|time_stamp|gateway_id|temperature_value|humidity_value|fan_value|beam_value|light1_value|light2_value|
|---|---|---|---|---|---|---|---|
|2019/06/13|12346asd435|26.4|60|56|83|ture|false|
