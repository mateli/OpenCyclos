
Behaviour.register({
	'#changePinForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + memberId;
		}
	},
	
	'#unblockPinButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/unblockPin?memberId=" + memberId;
		}
	},
	
	'a.default': function(a) {
		setPointer(a);
		a.onclick = function() {			
			self.location = pathPrefix + "/notificationPreferences?memberId=" + memberId;
		}		
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
		}
	}

});

Event.observe(self, "load", function() {
	var field = getObject('credentials') || getObject('newPin');
	setFocus(field);
});