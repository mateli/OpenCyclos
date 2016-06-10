function validatePassword(field) {
	var value = field.value;
	if (value.length > 0) {
		if (value.length < passwordRange.min) {
			alert(passwordTooSmallError);
			field.focus();
			return false;
		} else if (value.length > passwordRange.max) {
			alert(passwordTooLargeError);
			field.focus();
			return false;
		} else {
			var accepted = numericPassword ? JST_CHARS_NUMBERS : JST_CHARS_BASIC_ALPHA;
			if (!onlySpecified(value, accepted)) {
				alert(numericPassword ? mustBeNumericError : mustBeAlphaNumericError);
				field.focus();
				return false;
			}
			if (/AVOID_OBVIOUS/.test(policy)) {
				var tooSimple = value == getValue("member(user).username");
				if (!tooSimple) {
					var diffs = [];
					for (var i = 1, len = value.length; i < len; i++) {
						var current = value.charAt(i);
						var previous = value.charAt(i - 1);
						diffs.push(current - previous);
					}
					diffs = diffs.uniq();
					tooSimple = diffs.length == 1;
				}
				if (tooSimple) {
					alert(passwordTooSimpleError);
					field.focus();
					return false;
				}
			}			
			
			if (/LETTERS_NUMBERS/.test(policy)) {
				if (!/\d/.test(value) || !/[a-z|A-Z]/.test(value)) {
					alert(passwordMustIncludeLettersNumbersError);
					field.focus();
					return false;
				}
			}
		}
	}
	return true;
}

function setPostAction(action) {
	var postAction = this.elements["postAction"];
	if (postAction == null) {
		//Create the hidden if not found
		postAction = document.createElement("input");
		postAction.setAttribute("type", "hidden");
		postAction.setAttribute("name", "postAction");
		this.appendChild(postAction);
	}
	postAction.value = action;
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			if (!validatePassword($('_password'))) {
				return false;
			}
			var password = $("_password").value;
			var confirmation = $("_confirmPassword").value;
		    setValue("operator(user).password", password);
		    setValue("confirmPassword", confirmation);
			return requestValidation(form);
		}
	},
	
	'#saveAndNewButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'new');
	},
	
	'#saveAndOpenProfileButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'openProfile');
	}
});

Event.observe(self, "load", function() {
	getObject("operator(user).username").focus();
})