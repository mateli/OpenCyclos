Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var memberId = parseInt(getValue('memberId'), 10);
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else if (!isNaN(memberId) && memberId > 0) {
				self.location = pathPrefix + "/memberAds?memberId=" + memberId;
			} else {
				self.location = pathPrefix + "/searchAds";
			}
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['pictureFile', 'captionText']);
			var disable = getValue('notExpirableCheck');
			disablePublicationPeriod(disable, false);
			getObject('ad(title)').focus();
		}
	},
		
	'#newButton': function(button) {
		button.onclick = function() {
			var memberId = getValue("ad(owner)");
			self.location = pathPrefix + "/editAd?memberId=" + memberId;
		}
	},
	
	'#notExpirableCheck': function(check) {
		check.onclick = function() {
			var isChecked = check.checked;
			disablePublicationPeriod(isChecked, false);
		}
	},
	
	'#addPictureCheck': function(check) {
		check.onclick = function() {
			var isChecked = check.checked;
			showTrUploadPicture(isChecked);
			if (isChecked) {
				$('captionText').focus();
				enableField($('pictureFile'));
				enableField($('captionText'));
			} else {
				setValue('pictureFile', '');
				setValue('captionText', '');
				disableField($('pictureFile'));
				disableField($('captionText'));
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
	var disable = getValue('notExpirableCheck');
	disablePublicationPeriod(disable, true);
	
	var check = $('addPictureCheck');
	if (check) {
		check.checked = false;
	}
	if (typeof(maxImages) == "boolean") {
		showTrPictureCheck(!maxImages);
	}
	$$('.imageContainerDiv').min().container.onRemove=function() {
		showTrPictureCheck(true);
		if (check) {
			showTrUploadPicture(check.checked);
		}
	}
	if (isEmpty(getValue("ad(id)"))) {
		enableFormForInsert();
	}
})

function showTrPictureCheck(showTrCheck) {
	Element[showTrCheck ? 'show' : 'hide']("trPictureCheck");
	Element[showTrCheck ? 'hide' : 'show']("trMaxPictures");
	if (showTrCheck) {
		if (!isEmpty(getValue("picture"))) {
			var check = $('addPictureCheck');
			check.checked = true;
			check.onclick();
		}
	} else {
		setValue("pictureFile", "");
		setValue("captionText", "");
	}
}

function showTrUploadPicture(showTrUp) {
	$$('tr.trPicture').each(function(tr) {
		Element[showTrUp ? 'show' : 'hide'](tr);
	});
}

function disablePublicationPeriod(disablePeriod, firstTime) {
	if (disablePeriod) {
		setValue('publicationDate', '');
		setValue('expiryDate', '');
	}
	if (!firstTime) {
		if (disablePeriod) {
			disableField('publicationDate');
			disableField('expiryDate');
		} else {
			enableField('publicationDate');
			enableField('expiryDate');
		}
	}
}

/*
Overrides the version from all.js to prevent that the new button is disabled after clicking cancel
*/
function modifyResetClick() {
	this.form.reset();
	disableFormFields.apply(this.form);
	enableField($('newButton'));
}
