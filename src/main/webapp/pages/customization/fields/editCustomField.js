function getType(name) {
	var type = null;
	types.each(function(current) {
		if (current.name == name) {
			type = current;
			throw $break;
		}
	});
	return type;
}

function getControl(name) {
	var control = null;
	controls.each(function(current) {
		if (current.name == name) {
			control = current;
			throw $break;
		}
	});
	return control;
}

function initTypes() {
	var select = $("typeSelect");
	types.each(function(type) {
		type.controls = type.controls.map(getControl);
		if (select != null) {
			addOption(select, type, false, "description", "name", "defaultSelected");
		}
	});
}

function updateParent() {
	var parent = getValue("field(parent)");
	var method = isEmpty(parent) ? 'show' : 'hide';
	$$('tr.rootField').each(function(tr) {
		Element[method](tr)
	})
	if (!isEmpty(parent)) {
		setValue('controlSelect', 'SELECT');
	}
	Element[method]('trControl')
}

function updateFields() {
	var type = getType(getValue("field(type)"));
	if (type == null) {
		return;
	}
	var trSize = $("trSize");
	var trValidation = $("trValidation");
	var trPattern = $("trPattern");
	var trAllSelected = $("trAllSelected");
	var trRangeConstraint = $("trRangeConstraint");
	var trValidatorClass = $("trValidatorClass");
	var trCanHide = $("trCanHide");
	var trParent = $("trParent");

	var controlSelect = getObject("field(control)");
	var currentSelectedControl = getValue(controlSelect);
	setOptions(controlSelect, type.controls, false, false, "description", "name");
	setValue(controlSelect, currentSelectedControl || selectedControl);
	var control = getValue(controlSelect);
	
    if (type.name == "BOOLEAN")  {
        Element.hide(trSize);
        Element.hide(trValidation);
        setValue("field(size)", "DEFAULT");
    } else if (type.name == "MEMBER")  {
        Element.hide(trSize);
        Element.show(trValidation);
        setValue("field(size)", "DEFAULT");
	} else {
		Element.show(trSize);
		Element.show(trValidation);
	}

	if (type.name == "STRING") {
		var hideIfRichEditorShowOtherwise = Element[control == 'RICH_EDITOR' ? 'hide' : 'show'];
		hideIfRichEditorShowOtherwise(trPattern);
		hideIfRichEditorShowOtherwise(trSize);
		if (trCanHide) {
			hideIfRichEditorShowOtherwise(trCanHide);
		}
		Element.show(trRangeConstraint);
		$$(".unique").each(function(e) {Element.show(e);});
		if (control == 'RICH_EDITOR' && $('memberCanHide')) {
			$('memberCanHide').checked = false
		}
	} else {
		Element.hide(trPattern);
		Element.hide(trRangeConstraint);
		$$(".unique").each(function(e) {Element.hide(e);e.checked=false;});
		if (trCanHide) {
			Element.show(trCanHide);
		}
		setValue("field(pattern)", "");
		setValue("field(validation).lengthConstraint.min", "");
		setValue("field(validation).lengthConstraint.max", "");
	}

	if (type.name == "ENUMERATED" && control == "SELECT") {
		Element.show(trAllSelected);
	} else {
		Element.hide(trAllSelected);
		setValue("field(allSelectedLabel)", "");
	}
	
	trParent[type.name == "ENUMERATED" ? "show" : "hide"]();
	trValidatorClass[type.name == "ENUMERATED" ? "hide" : "show"]();
}

function togglePossibleValueEdit(flag) {
	Element[flag ? 'hide' : 'show']('saveButton');
	Element[flag ? 'hide' : 'show']('newPossibleValueButton');
	Element[!flag ? 'hide' : 'show']('editPossibleValueForm');
	Element[flag ? 'hide' : 'show']('possibleValues');
	var parentValues = $('parentValues')
	if (parentValues) {
		Element[flag ? 'hide' : 'show'](parentValues);
		var parentValueIdHidden = $('parentValueIdHidden');
		var parentValueDisplay = $('parentValueDisplay');
		if (parentValueDisplay) {
			var parentValueSelect = $('parentValueSelect');
			var selectedOption = parentValueSelect.options[parentValueSelect.selectedIndex];
			parentValueIdHidden.value = selectedOption.value;
			parentValueDisplay.value = selectedOption.text;
		}
	}
}

function editPossibleValue(id) {
	var value = "";
	var enabled = true;
	var isDefault = false;
	var fieldToFocus;
	if (id == null) {
		id = "";
		Element.show('possibleValueTitleInsert');
		Element.hide('possibleValueTitleModify');
		Element.show('trMultipleValues');
		Element.hide('trSingleValue');
		fieldToFocus = getObject("multipleValues");
		Element.hide('movePossibleValue');
	} else {
		value = $('tdPossibleValueValue_' + id).firstChild.data;
		enabled = booleanValue($('tdPossibleValueEnabled_' + id).getAttribute("isEnabled"));
		isDefault = booleanValue($('tdPossibleValueDefault_' + id).getAttribute("isDefault"));
		Element.hide('possibleValueTitleInsert');
		Element.show('possibleValueTitleModify');
		Element.show('trSingleValue');
		Element.hide('trMultipleValues');
		fieldToFocus = getObject("possibleValue(value)");

		var movePossibleValue = $('movePossibleValue');
		movePossibleValue.options.length = 1; 
		$$('.possibleValue').each(function(td) {
			var possibleValueId = td.id.split("_")[1];
			if (id != possibleValueId) {
				addOption(movePossibleValue, new Option(td.innerHTML, possibleValueId));
			}
		});
		Element.show(movePossibleValue);
	}
	setValue("possibleValue(id)", id);
	setValue("possibleValue(value)", value);
	getObject("possibleValue(enabled)").checked = enabled;
	getObject("possibleValue(defaultValue)").checked = isDefault;
	togglePossibleValueEdit(true);
	self.location = "#editPossibleValue";
	fieldToFocus.focus();
}

Behaviour.register({
	'#typeSelect': function(select) {
		select.onchange = updateFields;
	},
	
	'#parentSelect': function(select) {
		select.onchange = updateParent;
	},

	'#controlSelect': function(select) {
		select.onchange = updateFields;
	},
	
	'#parentValueSelect': function(select) {
		select.onchange = function() {
			self.location = pathPrefix + "/editCustomField?nature=" + nature + "&fieldId=" + getValue("fieldId") + "&parentValueId=" + getValue(select); 
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			if (nature == 'MEMBER_RECORD') {
				self.location = pathPrefix + "/editMemberRecordType?memberRecordTypeId=" + getValue("field(memberRecordType)"); 
			} else if (nature == 'PAYMENT') {
				self.location = pathPrefix + "/editTransferType?accountTypeId=" + getValue("backToAccountTypeId") + "&transferTypeId=" + getValue("backToTransferTypeId"); 
			} else {
				self.location = pathPrefix + "/listCustomFields?nature=" + nature;
			}
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var askForUnhideConfirmation = (!isEmpty(getValue("field(id)"))) && originalCanHideValue && (!getValue("memberCanHide"));
			if (askForUnhideConfirmation && !confirm(unhideConfirmationMessage)) {
				//only in this case stop all and do nothing
				return false;
			}
			return requestValidation(form);
		}
	},
	
	'img.editPossibleValue': function(img) {
		setPointer(img);
		img.onclick = function() {
			editPossibleValue(img.getAttribute("possibleValueId"));
		}
	},
	
	'#newPossibleValueButton': function(button) {
		button.onclick = function() {
			editPossibleValue(null);
		}
	},
	
	'#cancelPossibleValueButton': function(button) {
		button.onclick = function() {
			togglePossibleValueEdit(false);
		}
	},
	
	'img.removePossibleValue': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removePossibleValueConfirmationMessage)) {
				self.location = pathPrefix + "/removeCustomFieldPossibleValue?nature=" + nature + "&fieldId=" + getValue("fieldId") + "&possibleValueId=" + img.getAttribute("possibleValueId");
			}
		}
	},
		
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['typeText', 'parentText']);
			getObject('field(name)').focus();
		}
	},
	
	'#movePossibleValue': function(select) {
		select.onchange = function() {
			var oldId = getValue("possibleValue(id)");
			var idx = select.selectedIndex;
			if (isEmpty(oldId) || idx <= 0) {
				return;
			}
			var option = select.options[idx];
			var msg = moveValueConfirmation;
			msg = replaceAll(msg, "#oldValue#", getValue("possibleValue(value)"));
			msg = replaceAll(msg, "#newValue#", option.text);
			if (confirm(msg)) {
				self.location = pathPrefix + "/moveCustomFieldPossibleValue?nature=" + nature + "&oldValueId=" + oldId + "&newValueId=" + option.value;
			} else {
				select.selectedIndex = 0;
			}
		}
	}
});

Event.observe(self, "load", function() {
	initTypes();
	getObject("field(name)").focus();
	if (isEmpty(getValue("field(id)"))) {
		enableFormForInsert();
	} else {
		updateParent();
	}
	updateFields();
});
