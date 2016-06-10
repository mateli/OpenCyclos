Behaviour.register({
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['typeName']);
			try {
				Form.findFirstElement(button.form).focus();
			} catch (e) {}
		}
		// This event is been processed before the above block, so the logic must be inverse
		Event.observe(button, "click", function() {
			if ($('tdCopyButton')) {
				if (button.value == cancelLabel) {
					$('tdCopyButton').hide();
				} else {
					$('tdCopyButton').show();
				}
			}
		});
	},
	
	'#copyButton': function(button) {
		button.onclick = function() {
			var baseMemberRecordId = getValue("memberRecord(id)");
			if (!isEmpty(baseMemberRecordId)) {
				self.location = pathPrefix + "/editMemberRecord?baseMemberRecordId=" + baseMemberRecordId;
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			if (!isEmpty(baseMemberRecordId)) {
				self.location = pathPrefix + "/editMemberRecord?memberRecordId=" + baseMemberRecordId;
			} else if (isFlat && !returnToSearch) {
				self.location = pathPrefix + "/flatMemberRecords?typeId=" + getValue("typeId") + "&elementId=" + getValue("elementId") + "#memberRecord_" + getValue("memberRecord(id)");
			} else {
				self.location = pathPrefix + "/searchMemberRecords";
				//self.location = pathPrefix + "/searchMemberRecords?typeId=" + getValue("typeId") + "&elementId=" + getValue("elementId");
			}
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	var id = parseInt(getValue("memberRecord(id)"));
	if (isNaN(id) || id <= 0) {
		enableFormForInsert();
	}
});
