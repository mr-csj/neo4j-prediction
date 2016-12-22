$(function () {
    $('#searchBtn').click(function(){
        pageaction();
    });
});

var currentPageData = null ;
var pageaction = function(){
    $.get('./win_loss?t='+new Date().getTime(),{
        t1:$("#t1").val(), t2:$("#t2").val()
    },function(data){
        currentPageData = data;
        fillData(currentPageData);
    });
}

function fillData(data){
    $('#netContent').text( '甲队平均净赢：'+data.netwin.toFixed(2));

    var $head = $('#theadContent').empty();
    var head = "";
    head += '<tr>';
    if(data.met == 1){
        head += '<th>年度</th>';
        head += '<th>赢场</th>';
        head += '<th>输场</th>';
    }else{
        head += '<th>比赛历史：[甲队,其它球队],...</th>';
    }
    head += '</tr>';
    $head.append($(head));

    var $list = $('#tbodyContent').empty();
    $.each(data.content,function(k,v) {
        var html = "";
        html += '<tr> ';
        if(data.met == 1) {
            html += '<td>' + (v.year == null ? '' : v.year) + '</td>' +
            '<td>' + (v.win == null ? '' : v.win) + '</td>' +
            '<td>' + (v.loss == null ? '' : v.loss) + '</td>';
        }else{
            html += '<td>' + (v.paths == null ? '' : v.paths) + '</td>';
        }
        html +='</tr>' ;

        $list.append($(html));
    });
}

