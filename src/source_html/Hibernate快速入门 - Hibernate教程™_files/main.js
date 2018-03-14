var entry ='/v3.php';
jQuery(document)
		.ready(function($) {
			$(".qrcode").hover(function() {
				// alert('show');
				$("#bottom-qrcode").show();
			}, function() {
				// alert('hide');
				$("#bottom-qrcode").hide();
			});

			$(window).scroll(
					function() {
						if ($(window).scrollTop() >= 100) {
							$(".to-top").fadeIn();
							if ($('#htmlfeedback-container').length) {
								if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i
										.test(navigator.userAgent)) {
									// some code..
								} else {
									$("#htmlfeedback-container")
											.show();
								}
							}
						} else {
							$(".to-top").fadeOut();
						}
					});
			// 
			$(".to-top").click(function(event) {
				$('body').animate({
					scrollTop : 0
				}, 100);
				return false;
			});

	// init function list ...
	view_times();
	login_state();
});

$("#adv-javalive").click(function(){
	var postdata = {'type': '1'};	
	$.ajax({
		url : entry+'?app=adv',
		type : 'post',
		dataType : 'json',
		data : postdata,
		error : function() {},
		success : function(data, textStatus) {
			//$("#click_times").html(data.rs);
		}
	});
})

$("#adv-w3cschool").click(function(){
	
	var postdata = {'type': '2'};	
	$.ajax({
		url : entry+'?app=adv',
		type : 'post',
		dataType : 'json',
		data : postdata,
		error : function() {},
		success : function(data, textStatus) {
			//$("#click_times").html(data.rs);
		}
	});
})


// 查看次数
function view_times(){
	var article_id = $("#article_id").val();
	var artype = $("#artype").val();

	var postdata = {'article_id': article_id, 'artype': artype};
	
	$.ajax({
		url : entry+'?app=article&act=view_times',
		type : 'post',
		dataType : 'json',
		data : postdata,
		error : function() {},
		success : function(data, textStatus) {
			//$("#click_times").html(data.rs);
		}
	});
}

// 用户登录状态信息
function login_state(){
	$.ajax({
		url : entry+'?app=login&act=state',
		type : 'get',
		dataType : 'html',
		error : function() {},
		success : function(data, textStatus) {			
			$("#login-state").html(data);
		}
	});
}
