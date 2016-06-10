function isFileMappingInsert() {
	var id = parseInt(getValue("fileMapping(id)"));
	return (isNaN(id) || id == 0);
}

function switchNature() {
	if (getValue('fileMapping(nature)') == 'CSV') {
		$$('.trCustom').each(function(tr) {
			Element['hide'](tr);
		});
		$$('.trWithFields').each(function(tr) {
			Element['show'](tr);
		});
		$$('.trCSV').each(function(tr) {
			Element['show'](tr);
		});
		switchNumberFormat();
	} else {
		$$('.trWithFields').each(function(tr) {
			Element['hide'](tr);
		});
		$$('.trCSV').each(function(tr) {
			Element['hide'](tr);
		});
		$$('.trCustom').each(function(tr) {
			Element['show'](tr);
		});
	}
}

function switchNumberFormat() {
	if (getValue('fileMapping(numberFormat)') == 'FIXED_POSITION') {
		$$('.tdWithSeparator').each(function(td) {
			Element['hide'](td);
		});
		$$('.tdFixedPosition').each(function(td) {
			Element['show'](td);
		});
	} else {
		$$('.tdFixedPosition').each(function(td) {
			Element['hide'](td);
		});
		$$('.tdWithSeparator').each(function(td) {
			Element['show'](td);
		});
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#showFileMappingLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			$('fileMappingDescription').hide();
			$('fileMappingTable').show();
		}
	},
	
	'#natureSelect': function(select) {
		select.onchange = switchNature;
	},
	
	'#numberFormatSelect': function(select) {
		select.onchange = switchNumberFormat;
	},
	
	'#resetButton': function(button) {
		button.onclick = function() {
			if (confirm(resetFileMappingConfirmation)) {
				self.location = pathPrefix + "/resetFileMapping?fileMappingId=" + getValue("fileMapping(id)");
			}
		}
	},
	
	'#modifyFileMappingButton': function(button) {
		button.onclick = function() {
			modifyButtonName = "modifyFileMappingButton";
			saveButtonName = "saveFileMappingButton";
			enableFormFields.apply(button.form);
		}
	},
	
	'#newFieldMappingButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editFieldMapping?fileMappingId=" + getValue("fileMapping(id)");
		}
	},
	
	'#changeOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setFieldMappingsOrder?fileMappingId=" + getValue("fileMapping(id)");
		}
	},
	
	'img.fieldMappingDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editFieldMapping?fileMappingId=" + getValue("fileMapping(id)") + "&fieldMappingId=" + img.getAttribute("fieldMappingId");
		}
	},
	
	'img.fieldMappingRemove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeFieldMappingConfirmation)) {
				self.location = pathPrefix + "/removeFieldMapping?fieldMappingId=" + img.getAttribute("fieldMappingId");
			}
		}
	}
	
});

Event.observe(self, "load", function() {
	if (isFileMappingInsert()) {
		modifyButtonName = "modifyFileMappingButton";
		saveButtonName = "saveFileMappingButton";
		enableFormForInsert();
		$('fileMappingDescription').show();
	} else {
		$('fileMappingTable').show();
	}
	switchNature();
});