app.launchApp("TikTok");
auto("fast");
threads.start(function () {
    console.show();
    console.setTitle("Log", "#ff11ee00", 30);
    console.setCanInput(false);
});
const token = readFile("token.txt");
const praiseText = readFile("praise.txt");
const host = readFile("host.txt");
const width = device.width
const height = device.height
toRequstPermiss()

// 读取未点赞图片
var unLikeImg = images.read("/sdcard/follow/icon_tiktok_un_like.webp");
// 读取点赞图片
var likeImg = images.read("/sdcard/follow/icon_tiktok_like.webp");
// 解析json
parsePraiseJson()
//toTest()

function toRequstPermiss() {
    //申请权限并自动点击
    threads.start(function () {
        try {
            sleep(1000)
            var buttonList = classNameContains("Button").find();
            console.log("buttonList:" + buttonList.size());
            // var beginBtn = classNameContains("Button").textMatches(/(允许|立即开始|统一|START NOW)/).findOne(1000);
            var beginBtn = buttonList.get(1)
            if (beginBtn) {
                console.log("find beginBtn");
                clickView(beginBtn)
            } else {
                console.log("not find beginBtn");
                exit()
            }
        } catch (e) {
            console.log("requestScreenCapture fail:" + e);
            exit()
        }

    });
    if (!requestScreenCapture()) {
        console.log("requestScreenCapture fail");
        // exit();
    } else {
        console.log("requestScreenCapture success");
    }
}

function toTest() {
    sleep(1000)
    var likePoint = findLikeView(likeImg,0.2)
        // 已经点赞
    if (likePoint) {
         console.warn("The current video has been praised")
    }
}

function parsePraiseJson() {
    try {
        sleep(1000)
        var jsonArray = JSON.parse(praiseText);
        for (var i = 0; i < jsonArray.length; i++) {
            var praiseInfo = jsonArray[i];
            // console.log(praiseInfo)
            if (check(praiseInfo)) {
                console.log("Verification passed：" + praiseInfo.url)
                toPraise(praiseInfo)
            } else {
                console.log("Invalid connection：" + praiseInfo.url)
            }
        }
    } catch (e) {
        log("JSON parsing error：" + e);
    }
}


/**
 * 点赞
 */
function toPraise(praiseInfo) {
    try {
        app.startActivity({
            action: "android.intent.action.VIEW",
            data: praiseInfo.url
        });
        sleep(1000)
        // 去点赞
        doPraise(praiseInfo)
    } catch (e) {
        log("JSON parsing error：" + e);
    }
}

/**
 * 进行点赞
 * @param praiseInfo
 */
function doPraise(praiseInfo) {
    var unLikePoint = findLikeView(unLikeImg,0.4)
    if (unLikePoint) {
        console.warn("The current video has not been praised")
        click(unLikePoint.x,unLikePoint.y)
        sleep(500)
        var likePoint = findLikeView(likeImg,0.2)
        if (likePoint != null) {
            addPraise(praiseInfo)
            console.log("praised success:" + praiseInfo.url)
        } else {
            console.log("praised fail:" + praiseInfo.url)
        }
    } else {
        var likePoint = findLikeView(likeImg,0.2)
        // 已经点赞
        if (likePoint) {
            console.warn("The current video has been praised")
            addEarlyPraise(praiseInfo)
        }
    }
}


/**
 * 查找未点赞的view
 */
function findLikeView(inputImage,threshold) {
    try {
        if(inputImage==null){
          console.log("inputImage==null")
          return null
        }
        //截图
        var img = captureScreen();
        sleep(500);
        var point = findImage(img, inputImage, {
                   region: [width - relativeSize(200), height/2,relativeSize(200),relativeSize(400)],
                   threshold: threshold,
                   weakThreshold:threshold
               })
        /*var point = images.matchTemplate(img, inputImage, {
            region: [width - relativeSize(200), height/2,relativeSize(200),relativeSize(500)],
            threshold: threshold,
            weakThreshold:threshold
        })*/
//        var point = findImage(img, inputImage)
        if (point) {
            console.log("image find:"  + "--x:" + point.x + "--y:" + point.y)
        } else {
            console.log("image not find：")
        }
        return point
    } catch (e) {
        console.log("captureScreen error：" + e)
    }
    return null
}


/**
 * 检查当前账户是否可以被关注
 * @param account
 */
function check(praiseInfo) {
    var url = host + "praiseAccount/praiseCheck";
    var data = {
        "userId": praiseInfo.userId,
        "followType": praiseInfo.followType
    };
    var resp = postRequst(url, data);
    return resp === true;
}

/**
 * 添加点赞记录
 * @returns {boolean}
 * @param followAccount
 */
function addPraise(praiseInfo) {
    try {
        var url = host + "praise/add";
        var data = {
            "praisedUserId": praiseInfo.userId,
            "praiseType": praiseInfo.followType,
            "videoId": praiseInfo.id
        };
        var resp = postRequst(url, data);
        return resp != null;
    } catch (e) {
        console.error("addPraise:" + e)
    }
    return false

}

/**
 * 添加早期的关注到库里
 * @param followAccount
 * @returns {boolean}
 */
function addEarlyPraise(praiseInfo) {
    try {
        var url = host + "praise/earlyAdd";
        var data = {
            "praisedUserId": praiseInfo.userId,
            "praiseType": praiseInfo.followType,
            "videoId": praiseInfo.id
        };
        var resp = postRequst(url, data);
        return resp != null;
    } catch (e) {
        console.error("addEarlyPraise:" + e)
    }
    return false

}


/**
 * 读取本地文件
 * @param name
 * @returns {string}
 */
function readFile(name) {
    var path = "/sdcard/follow/" + name
    var file = open(path);
    var text = file.read().trim();
    file.close();
    return text
}

function postRequst(url, data) {
    // 定义请求头
    var headers = {
        "Content-Type": "application/json", // 设置请求头为 JSON 类型
        "token": token
    };

    // 配置请求选项
    var options = {
        url: url,
        method: "POST",
        headers: headers,
        body: data
    };
    // 发送POST请求
    var res = http.postJson(url, data, options);
    var result = res.body.string();
    var resultObject = JSON.parse(result);
    if (resultObject.code === 200) {
        return resultObject.data
    } else {
        toast(resultObject.message);
        return null
    }

}

function clickView(view) {
    if (view == null) {
        console.log("The view is empty and cannot be clicked")
        return
    }
    var bounds = view.bounds();
    var centerX = bounds.left + (bounds.right - bounds.left) / 2;
    var centerY = bounds.top + (bounds.bottom - bounds.top) / 2;
    click(centerX, centerY);
}

function relativeSize(size) {
    return width * size / 1080.0
}



sleep(1000);
console.clear();
console.hide();
app.launchPackage("org.autojs.autoxjs.follow");
unLikeImg.recycle();
likeImg.recycle();
exit();







