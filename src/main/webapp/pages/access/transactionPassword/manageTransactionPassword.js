Behaviour.register({
	'#resetButton': function(button) {
		button.onclick = function() {
			if (!confirm(resetConfirmationMessage)) {
				return false;
			}
			setValue("block", false);
		}
	}, 
	
	'#blockButton': function(button) {
		button.onclick = function() {
			if (!confirm(blockConfirmationMessage)) {
				return false;
			}
			setValue("block", true);
		}
	}
});