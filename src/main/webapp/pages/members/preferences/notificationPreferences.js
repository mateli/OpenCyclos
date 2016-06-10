Behaviour.register({	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, [
				 	'notificationPreference(FROM_ADMIN_TO_GROUP_message)', 
					'notificationPreference(FROM_ADMIN_TO_MEMBER_message)', 
					'notEnabled'
			]);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#disableSmsButton': function(button) {
		button.onclick = function() {
			if (confirm(disableSmsConfirmation)) {
				var shouldSubmit = false;
				$$('.smsCheck').each(function(checkBox) {
					var isChecked = checkBox.checked; 
					if (isChecked) {
						checkBox.checked = false;
						shouldSubmit = true;
					}
				});
				if (shouldSubmit) {
					enableFormFields.apply(button.form, [
					                 				 	'notificationPreference(FROM_ADMIN_TO_GROUP_message)', 
					                 					'notificationPreference(FROM_ADMIN_TO_MEMBER_message)', 
					                 					'notEnabled'
					                 			]);
					$$('form')[0].submit();
				}
			}
		}
	},
	
	'.smsCheck': function(checkBox) {
		checkBox.onclick = updateDisableSmsButton;
	},
	
	'.checkAll': function(a) {
		setPointer(a);
		a.onclick = function() {
			doCheckUnckeckAll(a.getAttribute("messageType"), true);
		}
	},
	
	'.uncheckAll': function(a) {
		setPointer(a);
		a.onclick = function() {
			doCheckUnckeckAll(a.getAttribute("messageType"), false);
		}
	}
});

function updateDisableSmsButton() {
	var shouldSubmit = false;
	var hasAnySmsChecked = $$('.smsCheck').find(function(checkBox) {
		return checkBox.checked;
	});
	$('disableSmsButton')[hasAnySmsChecked ? 'show' : 'hide']();
}

function doCheckUnckeckAll(messageType, check) {
	$$('.' + messageType).each(function(checkBox) {
		if (checkBox.disabled) return;
		checkBox.checked = check;
	});
	if (messageType == 'sms') {
		updateDisableSmsButton();
	}
}

Event.observe(self, "load", updateDisableSmsButton);
