$(function () {
    $('#searchBtn').click(function(){
        pageaction();
    });
    //pageaction();
});

//分页的参数设置
var getOpt = function(){
    var opt = {
        items_per_page: 10,	//每页记录数
        num_display_entries: 3, //中间显示的页数个数 默认为10
        current_page:0,	//当前页
        num_edge_entries:1, //头尾显示的页数个数 默认为0
        link_to:"javascript:void(0)",
        prev_text:"上页",
        next_text:"下页",
        load_first_page:true,
        show_total_info:true ,
        show_first_last:true,
        first_text:"首页",
        last_text:"尾页",
        hasSelect:false,
        callback: pageselectCallback //回调函数
    }
    return opt;
}
//分页开始
var currentPageData = null ;
var pageaction = function(){
    $.get('./win_loss?t='+new Date().getTime(),{
        t1:$("#t1").val(), t2:$("#t2").val()
    },function(data){
        currentPageData = data;
        fillData(currentPageData);
        //$(".pagination").pagination(data.totalElements, getOpt());
    });
}

//var pageselectCallback = function(page_index, jq, size){
//    var html = "" ;
//    if(currentPageData!=null){
//        fillData(currentPageData);
//        currentPageData = null;
//    }else
//        $.get('./win_loss?t='+new Date().getTime(),{
//            t1:$("#t1").val(), t2:$("#t2").val()
//        },function(data){
//            fillData(data.content);
//        });
//}

//填充数据
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
        head += '<th>赢场对比：甲队,乙队...</th>';
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

