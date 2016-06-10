Behaviour.register({
	'#comments': function(textarea) {
		new SizeLimit(textarea, 1000);
		textarea.focus();
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var memberId = parseInt(getValue("memberId"), 10) || parseInt(getValue("reference(to)"), 10);
			var params = $H();
			params.set("memberId", memberId);
			backToLastLocation(params);
		}
	},
	
	'#removeButton': function(button) {
		button.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeReference?referenceId=" + getValue("reference(id)") + "&memberId=" + getValue("reference(to)");
			}
		};
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}	
});