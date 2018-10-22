<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>List Viewer</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div>
    <h3 style="text-align: center">Type in your text here</h3>
    <div>
        <ol id="ordered-list" class="list" style="cursor: pointer">
            <%--@elvariable id="list" type="java.util.List"--%>
            <c:forEach items="${list}" var="text">
                <li id="${text.id}" onclick="showButtons()">
                    <p>${text.text}</p>
                    <span style='display: none'>
                            <button onclick="moveUp()">up</button>
                            <button onclick="moveDown()">down</button>
                            <button onclick="showEdit()">edit</button>
                            <button onclick="deleteRow()">delete</button>
                        <div style="display: none">
                            <label>
                                <input type="text" >
                            </label>
                            <button onclick="save()">save</button>
                        </div>
                    </span>
                </li>

            </c:forEach>
        </ol>
    </div>
    <div>
        <input type="text" id="text" name="text" placeholder="Type in your text here">
        <button id="addTextButton">Add text</button>
    </div>
</div>
<script>

    $(document).ready(function () {
        $("#addTextButton").click(function () {
            let input = $("#text");
            $.ajax({
                url: "/add-text",
                method: "POST",
                data: input.val(),
                dataType: "text",
                contentType: "text/html;charset=UTF-8",
                success: function (response) {
                    let json = JSON.parse(response);
                    $("#ordered-list").append(
                        "<li id="+ Object.keys(json)[0] +" onclick='showButtons()'>" +
                        "<p>" + json[Object.keys(json)[0]] + "</p>" +
                        "<span style='display: none'>" +
                        "<button onclick='moveUp()'>up</button>" +
                        "<button onclick='moveDown()'>down</button>" +
                        "<button onclick=\"showEdit()\">edit</button>" +
                        "<button onclick='deleteRow()'>delete</button>"+
                        "<div style=\"display: none\">" +
                        "<label>" +
                        "<input type=\"text\">" +
                        "</label>" +
                        "<button onclick='save()'>save</button>" +
                        "</div>" +
                        "</span>" +
                        "</li>");
                },
                error: function (error) {
                    console.log(error);
                }
            });
            input.val(null);
        });

    });

    function showEdit() {
        let curr = $(event.target).parent().find("div");
        curr.find("input").val(curr.parent().parent().find("p").text());
        if (curr.css("display") === "block") {
            curr.css("display", "none");
        }
        else {
            curr.css("display", "block");
        }
    }

    function showButtons() {
        let curr = $(event.target).parent().find("span");
        if (curr.css("display") === "none") {
            curr.css("display", "block");
        }
        else {
            curr.css("display", "none");
        }
    }

    function save() {
        let parentItem = $(event.target).parent(),
            text = parentItem.find("input").val(),
            _currentValue = parentItem.parent().parent().attr("id");
        $.ajax({
            url: "/edit",
            method: "POST",
            data: JSON.stringify({
              "currentValue" : _currentValue,
              "value" : text
            }),
            contentType : "application/json;charset=UTF-8",
            // dataType: "json",
            success: function(response){
                parentItem.parent().parent().find("p").text(response);
            },
            error: function (error) {
                console.log(error);
            }

        })

    }

    function deleteRow() {
        let parentItem = $(event.target).parent(),
            text = parentItem.parent().attr("id");
        $.ajax({
            url: "/delete-row",
            method: "POST",
            data: text,
            contentType: "text/html;charset=UTF-8",
            // dataType: "text",
            success: function(response){
                parentItem.parent().remove();
            },
            error: function (error) {
                console.log(error);
            }

        })
    }

    function moveUp() {
        let parentItem = $(event.target).parent(),
            text = parentItem.parent().attr("id");
        $.ajax({
            url: "/move-up",
            method: "POST",
            data: text,
            contentType: "text/html;charset=UTF-8",
            // dataType: "text",
            success: function(response){
                parentItem.parent().attr("id",parentItem.parent().prev().attr("id") );
                parentItem.parent().prev().attr("id",text);
                parentItem.parent().insertBefore(parentItem.parent().prev());
            },
            error: function (error) {
                console.log(error);
            }

        })
    }

    function moveDown() {
        let parentItem = $(event.target).parent(),
            text = parentItem.parent().attr("id");
        $.ajax({
            url: "/move-down",
            method: "POST",
            data: text,
            contentType: "text/html;charset=UTF-8",
            // dataType: "text",
            success: function(response){
                parentItem.parent().attr("id",parentItem.parent().next().attr("id") );
                parentItem.parent().next().attr("id",text);
                parentItem.parent().next().insertBefore(parentItem.parent());
            },
            error: function (error) {
                console.log(error);
            }

        })
    }

</script>

</body>
</html>
