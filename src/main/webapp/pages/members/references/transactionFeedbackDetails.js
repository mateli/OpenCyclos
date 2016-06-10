Behaviour.register({
	'#modifyButton': function(button) {
		button.onclick = function() {
			modifyButtonName = "modifyButton";
			saveButtonName = "saveButton";
			enableFormFields.apply(button.form);
		}
	},
	
	'#commentModifyButton': function(button) {
		button.onclick = function() {
			modifyButtonName = "commentModifyButton";
			saveButtonName = "commentSaveButton";
			enableFormFields.apply(button.form);
		}
	},
	
	'#replyModifyButton': function(button) {
		button.onclick = function() {
			modifyButtonName = "replyModifyButton";
			saveButtonName = "replySaveButton";
			enableFormFields.apply(button.form);
		}
	},
	
	'#comments': function(textarea) {
		new SizeLimit(textarea, 1000);
		textarea.focus();
	},

	'#replyComments': function(textarea) {
		new SizeLimit(textarea, 1000);
		textarea.focus();
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set("memberId", getValue("memberId"));
			backToLastLocation(params);
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}	
});

Event.observe(self, "load", function() {
	if (!editable) {
		if (canComment) {
			modifyButtonName = "commentModifyButton";
			saveButtonName = "commentSaveButton";
		} else if (canReply) {
			modifyButtonName = "replyModifyButton";
			saveButtonName = "replySaveButton";
		}
		enableFormForInsert(document.forms[0]);
	}
});