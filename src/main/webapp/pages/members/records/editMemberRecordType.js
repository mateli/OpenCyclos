function isInsert() {
	var id = parseInt(document.forms.editMemberRecordTypeForm.memberRecordTypeId.value);
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listMemberRecordTypes";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('memberRecordType(name)').focus();
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=MEMBER_RECORD&memberRecordTypeId=" + getValue("memberRecordTypeId");
		}
	},
	
	'#changeOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setCustomFieldOrder?nature=MEMBER_RECORD&memberRecordTypeId=" + getValue("memberRecordTypeId");
		}
	},
	
	'img.fieldDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=MEMBER_RECORD&fieldId=" + img.getAttribute("fieldId") + "&memberRecordTypeId=" + getValue("memberRecordTypeId");
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
			    var fieldId = img.getAttribute("fieldId");
			    var memberRecordTypeId = getValue("memberRecordTypeId");
				self.location = pathPrefix + "/removeCustomField?nature=MEMBER_RECORD&fieldId=" + fieldId + "&memberRecordTypeId=" + memberRecordTypeId;
			}
		}
	}
	
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});