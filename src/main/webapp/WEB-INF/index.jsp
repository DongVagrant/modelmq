<html>

<head>
    <script type="text/javascript" src="/js/jquery-1.4.4.js"></script>
    <script type="text/javascript">
        function testFunc() {
            $.ajax({
                type: "post",
                url: "/test/sendmq",
                dataType: "json",
                data: {
                    message: $("#input_text").val()
                },
                success: function (data) {
                    console.log(data);

                    // $("#result").text(JSON.stringify(data.obj));
                    $("#result").text(data.obj);

                }
            });
        }
    </script>
</head>
<body>
<h2>Hello World!</h2>
<input id="input_text" type="text">
<button id="input_button"  onclick="testFunc()" style="height: 20px;width: 100px">send mq</button>


<div style="margin-top: 20px">
    <textarea id="result" name="result" cols=30 rows=20></textarea>
</div>
</body>
</html>
