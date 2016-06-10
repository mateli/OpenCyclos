function submitLogin() {
	var params = $H();
	try { params.set('member', $('cyclosMember').value); } catch (e) {}
	params.set('principal', $('cyclosUsername').value);
	params.set('password', $('cyclosPassword').value);
	
	new Ajax.Request(context + "/do/externalLogin", {
		method: 'post',
		parameters: params.toQueryString(),
		onSuccess: function(request) {
			var status = request.responseText;
			if (status == 'SUCCESS') {
				top.location = context + loginAction;
			} else {
				alert(statusMessages[status]);
				$('cyclosPassword').value = '';
				if ($('cyclosUsername').value == '') {
					$('cyclosUsername').focus();
				} else {
					$('cyclosPassword').focus();
				}
			}
		}
	});
	
	return false;
}

Event.observe(self, "load", function() {
	//Apply the behavior
	$('loginForm').onsubmit = submitLogin;
});