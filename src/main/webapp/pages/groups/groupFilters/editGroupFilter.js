function isInsert() {
	var id = parseInt(getValue("groupFilterId"));
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listGroupFilters";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
		
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('groupFilter(name)').focus();
		}
	},
	
	
	'#newCustomizedFileButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroupFilterCustomizedFile?groupFilterId=" + getValue("groupFilterId");
		}
	},
	
	'img.customizedFileDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGroupFilterCustomizedFile?groupFilterId=" + getValue("groupFilterId") + "&fileId=" + img.getAttribute("fileId");
		}
	},
	
	'img.removeCustomizedFile': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(stopCustomizingConfirmation)) {
				self.location = pathPrefix + "/stopCustomizingGroupFilterFile?groupFilterId=" + getValue("groupFilterId") + "&fileId=" + img.getAttribute("fileId");
			}
		}
	},
	
	'img.previewCustomizedFile': function(img) {
		setPointer(img);
		var fileName = encodeURIComponent(img.getAttribute("fileName"));
		var type = img.getAttribute("fileType");
		img.onclick = function() {
			var url = pathPrefix + "/showCustomizedFile?type=" + type + "&fileName=" + fileName + "&groupFilterId=" + getValue("groupFilterId");
			window.open(url, 'pop', 'scrollbars,resizable,width=620,height=500,top=10,left=10');
		}
	}

});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});
