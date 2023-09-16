app.launchApp("TikTok");
auto("fast");
threads.start(function () {
    console.show();
    console.setTitle("log", "#ff11ee00", 30);
    console.setCanInput(false);
});
const width = device.width
const height = device.height
console.log("width:" + width + "--height:" + height)
const token = readFile("token.txt");
const accountText = readFile("account.txt");
const host = readFile("host.txt");
toRequstPermiss()
checkClickSearch()
var editText = className("EditText").findOne(2000);
if (editText != null) {
    console.log("Find EditInput");
    toFollow();
} else {
    console.log("Not Find EditInput");
}

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

/**
 * 尝试点击搜索按钮
 */
function checkClickSearch() {
    sleep(1500);
    click(width - relativeSize(50), relativeSize(150))
}

/**
 * 去关注
 */
function toFollow() {
    try {
        var jsonArray = JSON.parse(accountText);
        for (var i = 0; i < jsonArray.length; i++) {
            var followAccount = jsonArray[i];
            console.log("Check if account is available")
            if (check(followAccount)) {
                console.log("Check Success")
                doFollow(followAccount);
                back()
            } else {
                console.log("Check Fail")
            }
        }
    } catch (e) {
        log("JSON Parse Er：" + e);
    }
}


function doFollow(followAccount) {
    var followColor = "#FC2B55"
    var followedColor = "#F1F1F1"
    editText.setText(followAccount.account);
    sleep(100);
    checkClickSearch()
    sleep(1000);
    var followPoint = findFollowView(followColor)
    if (followPoint) {
        click(followPoint.x, followPoint.y)
        sleep(1000);
        var followedPoint = findFollowView(followedColor)
        if (followedPoint != null) {
            addFollow(followAccount)
            console.log("follow account:" + followAccount.account)
        } else {
            console.log("follow fail:" + followAccount.account)
        }
    } else {
        var followedPoint = findFollowView(followedColor)
        if (followedPoint) {
            addEarlyFollow(followAccount)
            console.log("followed early:" + followAccount.account)
        }
    }
}

function findViewByColor(color) {
    var now = new Date();
    var startSeconds = now.getSeconds();
    while (new Date().getSeconds() - startSeconds < 3) {
        console.log("Seconds:" + new Date().getSeconds())
        var point = findFollowView(color)
        if (point) {
            return point
        }
    }
    return null
}


/**
 * // 关注颜色 #EBEBEB  未关注颜色 #FB4872
 */
function findFollowView(color) {
    try {
        //截图
        var img = captureScreen();
        sleep(500);
        // var color = images.pixel(img, x, y);
        // console.log("点前xy颜色" + colors.toString(color))
        var findPoint = findColor(img, color, {
            region: [width - relativeSize(200), relativeSize(500), relativeSize(200), relativeSize(200)],
            threshold: 4
        })
        if (findPoint) {
            console.log("color:" + color + "--x:" + findPoint.x + "----y:" + findPoint.y)
            return findPoint
        } else {
            console.log("not find color")
        }
        return null
    } catch (e) {
        console.log("find color error：" + e)
    }
    return null
}


/**
 * 检查当前账户是否可以被关注
 * @param account
 */
function check(followAccount) {
    var url = host + "followAccount/followCheck";
    var data = {
        "userId": followAccount.userId,
        "followType": followAccount.followType
    };
    var resp = postRequst(url, data);
    return resp === true;
}

/**
 * 添加关注记录
 * @returns {boolean}
 * @param followAccount
 */
function addFollow(followAccount) {
    var url = host + "follow/add";
    var data = {
        "followUserId": followAccount.userId,
        "followType": followAccount.followType,
        "followAccount": followAccount.account
    };
    var resp = postRequst(url, data);
    return resp != null;
}

/**
 * 添加早期的关注到库里
 * @param followAccount
 * @returns {boolean}
 */
function addEarlyFollow(followAccount) {
    try {
        var url = host + "follow/earlyAdd";
        var data = {
            "followUserId": followAccount.userId,
            "followType": followAccount.followType,
            "followAccount": followAccount.account
        };
        var resp = postRequst(url, data);
        return resp != null;
    } catch (e) {
        console.error("addEarlyFollow:" + e)
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
        console.log("view is null,cannot be clicked")
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


console.clear();
console.hide();
sleep(2000);
app.launch("org.autojs.autoxjs.follow");
exit();







