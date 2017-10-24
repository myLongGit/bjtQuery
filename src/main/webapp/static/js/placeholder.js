$(function() {
    // 如果不支持placeholder，用jQuery来完成
    if(!isSupportPlaceholder()) {
    	$('input, textarea').not("input[type='password']")
	    .focus(function() {
	      var input = $(this);
	      if (input.val() == input.attr("placeholder")) {
	        input.val("");
	        input.removeClass("placeholder");
	      }
	    })
	    .blur(function() {
	      var input = $(this);
	      if (input.val() == "" || input.val() == input.attr("placeholder")) {
	        input.addClass("placeholder");
	        input.val(input.attr("placeholder"));
	      }
	    })
	    .blur()
	    .parents("form")
	    .submit(function() {
	      $(this).find("[placeholder]").each(function() {
	        var input = $(this);
	        if (input.val() == input.attr("placeholder")) {
	          input.val("");
	        }
	      });
	    });
        /**
         *  对password框的特殊处理
         * 1.创建一个text框 
         * 2.获取焦点和失去焦点的时候切换
         */
        $('input[type="password"]').each(
            function() {
                var pwdField    = $(this);
                var pwdVal      = pwdField.attr('placeholder');
                var pwdId       = pwdField.attr('id');
                var pwdClass = pwdField.attr('class');
                // 重命名该input的id为原id后跟1
                pwdField.after('<input id="' + pwdId +'1" type="text" class='+pwdClass+' value='+pwdVal+' autocomplete="off" />');
                var pwdPlaceholder = $('#' + pwdId + '1');
                pwdPlaceholder.show();
                pwdField.hide();

                pwdPlaceholder.focus(function(){
                    pwdPlaceholder.hide();
                    pwdField.show();
                    pwdField.focus();
                });

                pwdField.blur(function(){
                    if(pwdField.val() == '') {
                        pwdPlaceholder.show();
                        pwdField.hide();
                    }
                });
            }
        );
    }
});

// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
    var input = document.createElement('input');
    return 'placeholder' in input;
}