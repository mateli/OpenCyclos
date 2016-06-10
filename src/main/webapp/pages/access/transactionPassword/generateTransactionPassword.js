function generateTransactionPassword() {
	var callback = function(request, json) {
		if (!json) {
			alertAjaxError();
			return;
		}
		if (json.status) {
			var password = "";
			var tp = json.transactionPassword;
			for (var i = 0; i < tp.length; i++) {
				password += tp.charAt(i) + " ";
			}
			$("transactionPasswordTextTD").innerHTML = generatedMessage + " <b>" + password + "</b>";
			$("transactionPasswordButtonTD").innerHTML = "<input type='button' class='button' value=\"" + okLabel + "\" onclick=\"$('transactionPasswordDIV').innerHTML='';$('transactionPasswordDIV').style.display = 'none'\">";
		} else {
			alert(json.errorMessage);
		}
	}

	new Ajax.Request(pathPrefix + "/generateTransactionPassword", {
		method: "post",
		onSuccess: callback
	});
}