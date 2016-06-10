function natureChanged() {
	var nature = getValue("group(nature)");
	if (isInsert && nature != "OPERATOR") {
		var params = arrayToParams(ensureArray(getValue('group(nature)')), 'natures')  + "&" + arrayToParams(ensureArray(["NORMAL"]), 'status');
		findGroups(params, function(groups) {
			setOptions('groupsSelect', groups, true, false, 'name', 'id', null);
		});
	}
}

function removedChanged() {
	var isRemoved = getValue("group(status)") == 'REMOVED';
	$('trCopySettingsFrom')[isRemoved ? 'hide' : 'show']();
	setValue("baseGroupId", null);
	$('group(baseGroupId)').disabled = isRemoved;
}

function updateInitialGroup() {
	var initialCheck = $('initialCheck');
	if (!initialCheck) {
		return;
	}
	var isChecked = initialCheck.checked;
	$$("tr.initialGroup").each(isChecked ? Element.show : Element.hide);
}

function showHideCustomSmsContext() {
	var showCustom = $("customSmsCtxCheck").checked;
	
	$$('tr.basicSmsCtx').each(function(tr) {
   		tr[!showCustom ? 'show' : 'hide']();
   	});
	
	$$('tr.customSmsCtx').each(function(tr) {
   		tr[showCustom ? 'show' : 'hide']();
   	});   		
	
   	if (!showCustom) {
   		setValue('group(memberSettings).smsContextClassName', null);
	}	

}

Behaviour.register({

	'#naturesSelect': function(select) {
		select.onchange = natureChanged;
	},
	
	'#removedCheck': function(checkbox) {
		checkbox.onclick = removedChanged;
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listGroups";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['natureText', 'statusText', 'activeText']);
			var id = parseInt(getValue("groupId"));
			getObject('group(name)').focus();
		}
	},
	
	'#registrationAgreementSelect': function(select) {
		select.onchange = function() {
			var currentValue = getValue(select);
			var showForceAccept = !isEmpty(currentValue) && originalRegistrationAgreement != currentValue;
			$('forceAcceptTR')[showForceAccept ? 'show' : 'hide']();
		}
		select.onchange();
	},
	
	'#initialCheck': function(checkbox) {
		checkbox.onclick = updateInitialGroup;
	},
	
	'#groupPermissionsButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroupPermissions?groupId=" + getValue("groupId");
		}
	},
	
	'#newAccountButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroupAccountSettings?groupId=" + getValue("groupId");
		}
	},
	
	'img.accountDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGroupAccountSettings?groupId=" + getValue("groupId") + "&accountTypeId=" + img.getAttribute("accountTypeId");
		}
	},
	
	'img.removeAccount': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeAccountConfirmation)) {
				self.location = pathPrefix + "/removeGroupAccount?groupId=" + getValue("groupId") + "&accountTypeId=" + img.getAttribute("accountTypeId");
			}
		}
	},
	
	'#newCustomizedFileButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroupCustomizedFile?groupId=" + getValue("groupId");
		}
	},
	
	'img.customizedFileDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGroupCustomizedFile?groupId=" + getValue("groupId") + "&fileId=" + img.getAttribute("fileId");
		}
	},
	
	'img.removeCustomizedFile': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(stopCustomizingConfirmation)) {
				self.location = pathPrefix + "/stopCustomizingGroupFile?groupId=" + getValue("groupId") + "&fileId=" + img.getAttribute("fileId");
			}
		}
	},
	
	'img.previewCustomizedFile': function(img) {
		setPointer(img);
		var fileName = encodeURIComponent(img.getAttribute("fileName"));
		var type = img.getAttribute("fileType");
		img.onclick = function() {
			var url = pathPrefix + "/showCustomizedFile?type=" + type + "&fileName=" + fileName + "&groupId=" + getValue("groupId");
			window.open(url, 'pop', 'scrollbars,resizable,width=620,height=500,top=10,left=10');
		}
	},

	'#customSmsCtxCheck': function(checkbox) {
		checkbox.onclick = function(checkbox) {
			showHideCustomSmsContext();
		}
	}
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("group(id)"))) {
		enableFormForInsert();
	}

	var customSmsCtxCheck = $("customSmsCtxCheck");
	if (customSmsCtxCheck) {
		customSmsCtxCheck.checked=!isEmpty(getValue("group(memberSettings).smsContextClassName"));
		showHideCustomSmsContext();
	}
	
	updateInitialGroup();
	natureChanged();
});