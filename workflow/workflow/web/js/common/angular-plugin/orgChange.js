/**
 * Created by zhuliyong_2007@163.com on 2014/10/9.
 * 新增标签：org-change，即组织机构切换组件
 * TODO
 */
(function(window, angular, undefined) {
    'use strict';

    /* global -orgChange */
    var orgChangeModule = angular.module('orgChange', ['ng']);

    //orgChangeModule.provider('$routeParams', $RouteParamsProvider);

    orgChangeModule.directive('orgChange', orgChangeFactory);

    /**************************************************
     * dom-drag.js
     * 09.25.2001
     * www.youngpup.net
     **************************************************
     * 10.28.2001 - fixed minor bug where events
     * sometimes fired off the handle, not the root.
     **************************************************/

    var Drag = {

        obj : null,

        init : function(o, oRoot, minX, maxX, minY, maxY, bSwapHorzRef, bSwapVertRef, fXMapper, fYMapper)
        {
            o.onmousedown	= Drag.start;

            o.hmode			= bSwapHorzRef ? false : true ;
            o.vmode			= bSwapVertRef ? false : true ;

            o.root = oRoot && oRoot != null ? oRoot : o ;

            if (o.hmode  && isNaN(parseInt(o.root.style.left  ))) o.root.style.left   = "0px";
            if (o.vmode  && isNaN(parseInt(o.root.style.top   ))) o.root.style.top    = "0px";
            if (!o.hmode && isNaN(parseInt(o.root.style.right ))) o.root.style.right  = "0px";
            if (!o.vmode && isNaN(parseInt(o.root.style.bottom))) o.root.style.bottom = "0px";

            o.minX	= typeof minX != 'undefined' ? minX : null;
            o.minY	= typeof minY != 'undefined' ? minY : null;
            o.maxX	= typeof maxX != 'undefined' ? maxX : null;
            o.maxY	= typeof maxY != 'undefined' ? maxY : null;

            o.xMapper = fXMapper ? fXMapper : null;
            o.yMapper = fYMapper ? fYMapper : null;

            o.root.onDragStart	= new Function();
            o.root.onDragEnd	= new Function();
            o.root.onDrag		= new Function();
        },

        start : function(e)
        {
            var o = Drag.obj = this;
            e = Drag.fixE(e);
            var y = parseInt(o.vmode ? o.root.style.top  : o.root.style.bottom);
            var x = parseInt(o.hmode ? o.root.style.left : o.root.style.right );
            o.root.onDragStart(x, y);

            o.lastMouseX	= e.clientX;
            o.lastMouseY	= e.clientY;

            if (o.hmode) {
                if (o.minX != null)	o.minMouseX	= e.clientX - x + o.minX;
                if (o.maxX != null)	o.maxMouseX	= o.minMouseX + o.maxX - o.minX;
            } else {
                if (o.minX != null) o.maxMouseX = -o.minX + e.clientX + x;
                if (o.maxX != null) o.minMouseX = -o.maxX + e.clientX + x;
            }

            if (o.vmode) {
                if (o.minY != null)	o.minMouseY	= e.clientY - y + o.minY;
                if (o.maxY != null)	o.maxMouseY	= o.minMouseY + o.maxY - o.minY;
            } else {
                if (o.minY != null) o.maxMouseY = -o.minY + e.clientY + y;
                if (o.maxY != null) o.minMouseY = -o.maxY + e.clientY + y;
            }

            document.onmousemove	= Drag.drag;
            document.onmouseup		= Drag.end;

            return false;
        },

        drag : function(e)
        {
            e = Drag.fixE(e);
            var o = Drag.obj;

            var ey	= e.clientY;
            var ex	= e.clientX;
            var y = parseInt(o.vmode ? o.root.style.top  : o.root.style.bottom);
            var x = parseInt(o.hmode ? o.root.style.left : o.root.style.right );
            var nx, ny;

            if (o.minX != null) ex = o.hmode ? Math.max(ex, o.minMouseX) : Math.min(ex, o.maxMouseX);
            if (o.maxX != null) ex = o.hmode ? Math.min(ex, o.maxMouseX) : Math.max(ex, o.minMouseX);
            if (o.minY != null) ey = o.vmode ? Math.max(ey, o.minMouseY) : Math.min(ey, o.maxMouseY);
            if (o.maxY != null) ey = o.vmode ? Math.min(ey, o.maxMouseY) : Math.max(ey, o.minMouseY);

            nx = x + ((ex - o.lastMouseX) * (o.hmode ? 1 : -1));
            ny = y + ((ey - o.lastMouseY) * (o.vmode ? 1 : -1));

            if (o.xMapper)		nx = o.xMapper(y)
            else if (o.yMapper)	ny = o.yMapper(x)

            Drag.obj.root.style[o.hmode ? "left" : "right"] = nx + "px";
            Drag.obj.root.style[o.vmode ? "top" : "bottom"] = ny + "px";
            Drag.obj.lastMouseX	= ex;
            Drag.obj.lastMouseY	= ey;

            Drag.obj.root.onDrag(nx, ny);
            return false;
        },

        end : function()
        {
            document.onmousemove = null;
            document.onmouseup   = null;
            Drag.obj.root.onDragEnd(	parseInt(Drag.obj.root.style[Drag.obj.hmode ? "left" : "right"]),
                parseInt(Drag.obj.root.style[Drag.obj.vmode ? "top" : "bottom"]));
            Drag.obj = null;
        },

        fixE : function(e)
        {
            if (typeof e == 'undefined') e = window.event;
            if (typeof e.layerX == 'undefined') e.layerX = e.offsetX;
            if (typeof e.layerY == 'undefined') e.layerY = e.offsetY;
            return e;
        }
    };

    var masked=false;
// 全屏遮罩层
    function boxAlpha (){
        if (masked==false){
            maskLayer();
            masked=true;
        }
        else{
            $('#maskLayer').hide();
            masked=false;
        }
    }

    function createBody(){
        var tamp ='<div id="maskLayer" style="display:none">                                                                                    '+
            '    <div id="alphadiv" style="filter:alpha(opacity=50);-moz-opacity:0.5;opacity:0.5;width:100%;position: absolute;"></div>   '+
            '    <div id="drag" style="width:400px;position: relative;left:474px;top:40px">                                               '+
            '        <h3 id="drag_h"><b>组织机构切换</b><span ng-click="boxAlpha">关闭</span></h3>                                       '+
            '        <div id="drag_con">                                                                                                 '+
            '            <div id="nationAlpha">                                                                                         '+
            '                <div id="nationList">                                                                                      '+
            '                 <iframe width="100%" height="300px" frameborder="0" scrolling="auto" id="tree"  src="">         '+
            '                    </iframe>                                                                                                '+
            '                </div>                                                                                                       '+
            '            </div>                                                                                                           '+
            '        </div>                                                                                                               '+
            '    </div>                                                                                                                   '+
            '</div> ';

        return tamp;
    }

    function maskLayer(){
        var FW=document.body.scrollWidth;
        var FH=document.body.scrollHeight;
        var SH=window.screen.height;
        FH=FH<SH?SH:FH;
        $("#alphadiv").height(FH).width(FW);
        $('#maskLayer').show();
        $('#maskLayer_iframe').css({position:"absolute",left:"0px",top:"0px"}).height(FH).width(FW);
    }

    function draglayer(){
        var och=$("#drag").height();
        var ocw=$("#drag").width();
        var bsl = document.documentElement.scrollLeft || document.documentElement.scrollLeft;
        var bst = document.documentElement.scrollTop || document.documentElement.scrollTop;
        var bcw = document.documentElement.clientWidth || document.documentElement.clientWidth;
        var bch = document.documentElement.clientHeight || document.documentElement.clientHeight;
        /*
         !DOCTYPE声明bug调试
         var bsl = document.body.scrollLeft || document.documentElement.scrollLeft;
         var bst = document.body.scrollTop || document.documentElement.scrollTop;
         var bcw = document.body.clientWidth || document.documentElement.clientWidth;
         var bch = document.body.clientHeight || document.documentElement.clientHeight;
         */
        var osl = bsl + Math.floor( ( bcw - ocw ) / 2 );
        osl = Math.max( bsl , osl );
        var ost = bst + Math.floor( ( bch - och ) / 2 );
        ost = Math.max( bst , ost );

        $("#drag").css({"top":ost,"left":osl,"width":ocw}).show();
        var theHandle = document.getElementById("drag_h");
        var theRoot   = document.getElementById("drag");
        Drag.init(theHandle, theRoot);
    }
    $(window).resize(function (){
        if (masked==true){
            draglayer();
            maskLayer();
        }
    });


    var nation = {
        // 国籍输出
        Show : function(){
            var output='',flag,output2='';
            for (var i in na){
                output+='<li onclick="nation.Chk(\''+i+'\')">'+na[i]+'</li>';
            }
            $('#drag').width('370px');
            $('#nationList').html('<ul>'+output+'</ul>');
            // 鼠标悬停变色
            $('#nationAlpha li').hover(function(){$(this).addClass('over')},function(){$(this).removeClass('over')});
        },
        Chk : function(id){
            $('#btn_nation').val(na[id]);
            $('#nation').val(id);
            boxAlpha();
        }
    }




    function nationSelect(){
        var dragHtml ='<div id="nationAlpha">';		//国籍
        dragHtml+='		<div id="nationList"></div>';//国籍列表
        dragHtml+='</div>';
        //$('#drag_h').html('<b>请选择国籍</b><span onclick="boxAlpha()">关闭</span>');
        //$('#drag_con').html(dragHtml);

        //nation.Show();
        boxAlpha();
        draglayer();
    }

    /**
     * @ngdoc event
     * @name ngView#$viewContentLoaded
     * @eventType emit on the current ngView scope
     * @description
     * Emitted every time the ngView content is reloaded.
     */
    orgChangeFactory.$inject = ['$scope'];
    function orgChangeFactory($scope) {
        return {
            restrict: 'ECA',
            terminal: true,
            priority: 400,
            transclude: 'element',
            template: '<div>当前组织机构为:<input id="btn_nation" type="button" value="请选择组织机构" onclick = "nationSelect()" /> <em onclick="nationSelect()" ></em></div>',
            link: function(scope, element, attr, ctrl, $transclude) {
                scope.nationSelect = nationSelect;
                scope.boxAlpha = boxAlpha;
            }
        };
    }
})(window, window.angular);
