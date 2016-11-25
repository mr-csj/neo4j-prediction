$(function () {
    $('#searchBtn').click(function(){
        pageaction();
    });
    pageaction();
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
    $.get('./ration_list?t='+new Date().getTime(),{
    },function(data){
        currentPageData = data.content;
        $(".pagination").pagination(data.totalElements, getOpt());
    });
}

var pageselectCallback = function(page_index, jq, size){
    var html = "" ;
    if(currentPageData!=null){
        fillData(currentPageData);
        currentPageData = null;
    }else
        $.get('./ration_list?t='+new Date().getTime(),{
            size:size,page:page_index
        },function(data){
            fillData(data.content);
        });
}

//填充分页数据
function fillData(data){
    var $list = $('#tbodyContent').empty();
    $.each(data,function(k,v) {
        var html = "";
        html += '<tr> ' +
            '<td>' + (v.team == null ? '' : v.team) + '</td>' +
            '<td>' + (v.wins == null ? '' : v.wins) + '</td>' +
            '<td>' + (v.losses == null ? '' : v.losses) + '</td>' +
            '<td>' + (v.percentage == null ? '' : v.percentage.toFixed(3)) + '</td>';

        html +='</tr>' ;

        $list.append($(html));
    });
}
//分页结束
