Behaviour.register({

	'#modifyButton': function(button) {
		button.onclick = function() {
			var keep = ['admin(user).username', 'admin(group).name', 'admin(user).lastLogin'];
			enableFormFields.apply(button.form, keep);
			getObject('admin(name)').focus();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchAdmins";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});