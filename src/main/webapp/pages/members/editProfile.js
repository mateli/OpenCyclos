var keep = ['brokerText', 'creationDateText', 'loginText', 'groupText', 'groupFilters'];
if (!canChangeName) {
    keep.push('member(name)');
}
if (!canChangeEmail) {
    keep.push('member(email)');
}
if (!canChangeUsername) {
	keep.push('member(user).username');
}

Behaviour.register({
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, keep);
			['member(user).username', 'member(name)', 'member(email)'].each(function(name) {
				var object = getObject(name);
				if (!object.readOnly && !object.disabled) {
					object.focus();
					throw $break;
				}
			});
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				history.back();
			}
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
				setValue("pictureFile", "");
				setValue("captionText", "");
				disableField($('pictureFile'));
				disableField($('captionText'));
			}
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'input': function(checkbox) {
		var prefix = "chk_hidden_";
		if (checkbox.id.indexOf(prefix) >= 0) {
			checkbox.onclick = function() {
				$('hidden_' + this.id.substring(prefix.length)).value = this.checked;
			}.bindAsEventListener(checkbox);
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
	},
	
	'#resendEmailChangeValidation': function(a) {
	    setPointer(a);
	    a.onclick = function() {
	        self.location = pathPrefix + "/resendEmailChangeValidation?memberId=" + getValue("member(id)")
	    }
	}
	
});

Event.observe(self, "load", function() {
	var check = $('addPictureCheck');
	if (check) {
		check.checked = false;
	}
	if (typeof(maxImages) == "boolean") {
		showTrPictureCheck(!maxImages);
	}
	if (images) {
		images.onRemove=function() {
			showTrPictureCheck(true);
			if (check) {
				showTrUploadPicture(check.checked);
			}
		}
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
		setValue("picture", "");
		setValue("pictureCaption", "");
	}
}

function showTrUploadPicture(showTrUp) {
	$$('tr.trPicture').each(function(tr) {
		Element[showTrUp ? 'show' : 'hide'](tr);
	});
}