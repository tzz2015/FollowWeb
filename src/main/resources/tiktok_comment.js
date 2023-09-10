app.launchApp("TikTok");
auto("fast");
threads.start(function () {
    console.show();
    console.setTitle("Log", "#ff11ee00", 30);
    console.setCanInput(false);
});
toRequstPermiss()


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


function myrandom(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

// 生成一个随机字符串，包含数字和字母
function generateRandomString(length) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789！@#￥%……&*()_+-=~`';
    let result = '';

    for (let i = 0; i < length; i++) {
        var randomIndex = Math.floor(Math.random() * characters.length);
        result += characters.charAt(randomIndex);
    }

    return " " + result + " ";
}

var width = device.width
var height = device.height
var fruits = ["互粉互赞来咯。https://s1.link-nemo.com/bot/lyf/inrt-release.apk。",
    "Recommend a tool for mutual fans and likes to increase your fans quickly。https://s1.link-nemo.com/bot/lyf/inrt-release.apk。",
    "互粉互赞，来咯，一起用互粉互赞工具。https://s1.link-nemo.com/bot/lyf/inrt-release.apk。",
    "Come and use the mutual fans and likes tool together. https://s1.link-nemo.com/bot/lyf/inrt-release.apk。",
    "发现了一个互粉好帮手，一起来互粉吧。https://s1.link-nemo.com/bot/lyf/inrt-release.apk ",
    "I found a good helper for mutual fans, let’s start mutual fans together. https://s1.link-nemo.com/bot/lyf/inrt-release.apk"];

// 将一段文本插入另一段文本中间
function insertText(originalText, insertText, position) {
    return originalText.slice(0, position) + insertText + originalText.slice(position);
}

var maxDo = 100
while (maxDo > 0) {
    // var point = findLikeView("#EAE6E5")
    // if (point) {
    console.log("maxDo:" + maxDo)
    click(1000, 1550)
    // console.log("point.x:" + point.x + "-- point.y:" + point.y)
    maxDo -= 1
    var index = myrandom(0, fruits.length - 1)
    var inputText = generateRandomString(5) + fruits[index] + generateRandomString(5)
    // var inputText2 = insertText(inputText, generateRandomString(3), 10)
    doInput(inputText)
    /* } else {
         console.log("live")
         swipe(width / 2, height / 2, width / 2, 200, 500)
         sleep(200);
     }*/

}

function findLikeView(color) {
    try {
        //截图
        var img = captureScreen();
        sleep(500);
        // var color = images.pixel(img, x, y);
        // console.log("点前xy颜色" + colors.toString(color))
        var point = findColor(img, color, {
            region: [width - relativeSize(100), height / 3 / 2 - relativeSize(50), relativeSize(100), relativeSize(200)],
            threshold: 4
        })
        if (point) {
            console.log("color find:" + color)
            return point
        } else {
            console.log("color not find:" + color)
        }
    } catch (e) {
        console.log("find view error：" + e)
    }
    return null
}


function doInput(inptuText) {
    sleep(1000)
    console.log("width:" + width + "--height:" + height)
    // click(width / 2, height - 50)
    // console.log("click EditText")
    // sleep(800)
    var editText = className("EditText").findOne(1000);
    console.log("find editText：" + (editText != null))
    if (editText != null) {
        sleep(200)
        editText.setText(inptuText)
        console.log("set text")
        sleep(200)
        click(width - relativeSize(100), height - relativeSize(100))
        sleep(200)
        click(width - relativeSize(100), relativeSize(200))
        sleep(300)
        swipe(width / 2, height / 2, width / 2, 200, 500)
        sleep(500)
    } else {
        back()
        sleep(300)
        swipe(width / 2, height / 2, width / 2, 200, 500)
        sleep(500)
    }
}


function clickView(view) {
    if (view == null) {
        console.log("view未空，不能点击")
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
exit();
