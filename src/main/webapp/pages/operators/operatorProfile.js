var keep = ['operator(group).name', 'operator(user).lastLogin'];
if (!isMember) {
	keep.push('operator(name)');
	keep.push('operator(user).username');
}

Behaviour.register({

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, keep);
			if (isMember) {
				getObject('operator(name)').focus();
			} else {
				getObject('operator(email)').focus();
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchOperators";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'.customFieldContainer': function(span) {
		var isEditable = booleanValue(span.getAttribute("editable"));
		var field = $A(span.getElementsByTagName("input")).find(function(f) {
			return f.type != 'hidden';
		});
		if (field != null && !isEditable) {
			keep.push(field);
		}
	}
});