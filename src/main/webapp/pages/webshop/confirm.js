Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			var tt = getObject("transferType");
			var tp = $("_transactionPassword");
			if (tp) {
				setValue("transactionPassword", tp.value.toUpperCase());
			}
			if (!tp && tt.type == 'hidden') {
				return true;
			}
			return requestValidation(form);
		}
	},
	
	'#virtualKeyboard': function(div) {
		new VirtualKeyboard(div, "_transactionPassword", {
			'chars': chars, 
			'buttonStyle': 'width:35px', 
			'submitLabel': submitLabel, 
			'contrast': contrastLabel, 
			'clear': clearLabel,
			'buttonsPerRow': buttonsPerRow
		});
	},
	
	'#cancelButton': function(button) {
		button.onclick = function() {
			self.location = context + "/do/webshop/cancel";
		}
	}
});