/**
 * Created by Administrator on 2015/10/23 0023.
 */
var msgType=[
    "LOGIN",
    "LOGOUT",
    "MESSAGE",
    "LOGIN_SUCCESS",
    "LOGIN_ERROR",
    "ON_USER_LOGIN",
    "ON_USER_LOGOUT"
];
var MessageType={

    login:msgType[0],
    logout:msgType[1],
    message:msgType[2],
    loginSuccess:msgType[3],
    loginError:msgType[4],
    onUserLogin:msgType[5],
    onUserLogout:msgType[6]
};

var msg={
    type:"onMessage",
    from:"name1",
    to:"nam2",
    content:"data"
}

var onMessageListener;

function messageClient(userName,messageListener){


    var chat=new Object();
    onMessageListener=messageListener;
    chat.userName=userName;
    chat.url=wsHost+"/websocket";
    chat.connect=function(){

        this.websocket=new WebSocket(this.url+"/"+this.userName);
        this.websocket.onopen=function(event){

            var eventStr=JSON.stringify(event);
            console.log("on open event: "+eventStr);
            //client.send("connected success!");

        };
        this.websocket.onmessage=function(event){

            var msg=event.data;
           // console.log(event.data);
            var msg=JSON.parse(event.data);
            var messageType=msg.type;
            if(onMessageListener!=undefined){

                onMessageListener(msg);
                return ;
            }

            if(messageType==MessageType.loginSuccess){

                console.log("login success");

            }else if(messageType==MessageType.loginError){

            } else if(messageType==MessageType.message){

            }



        };
        this.websocket.onclose=function(event){

            var eventStr=JSON.stringify(event);
            console.log("on  close event: "+eventStr);

        };
        this.websocket.onerror=function(event){

            var eventStr=JSON.stringify(event);

            console.log("on  error event: "+eventStr);


        };


        chat.sendText=function(msg){

            console.log("send msg: "+msg);
            this.websocket.send(msg);

        };






    };
    chat.disconnect=function(){

        this.websocket.close();
    };
    return chat;
}


function sendMsg(){

    var msg =$("#msg").val();
    var targetUser=$("#tree").tree("getSelected");

    $("#messageBox").append("<p><span>"+chat.userName+"</span><br>"+msg+"</p>");

    var msgStr={
        "from":chat.userName,
        "to":targetUser.id,
        "content":msg,
        "type":msgType[1]

    }
    console.log("send "+JSON.stringify(msgStr));
    chat.sendText(JSON.stringify(msgStr));


}