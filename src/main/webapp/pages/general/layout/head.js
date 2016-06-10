var headBehaviour = {
	'input': function(input) {
		if (is.ie && (input.type == 'radio' || input.type == 'checkbox')) {
			input.style.border = "none";
		}
		if (Element.hasClassName(input, "linkButton")) {
			input.onclick = function() {
				var url = input.getAttribute("linkURL");
				if (url.indexOf("mailto:") < 0 && url.indexOf("http:") < 0  && url.indexOf("https:") < 0) {
					url = pathPrefix + "/" + url;
				}
				self.location = url;
			}
		}
		if (Element.hasClassName(input, "required")) {
			if (Element.hasClassName(input, "full")) {
				input.style.width = "95%";
			}
			new Insertion.After(input, "&nbsp;<span class='fieldDecoration' style='vertical-align:top'>*</span>");
		}
		if (Element.hasClassName(input, 'pattern')) {
			var pattern = input.getAttribute("maskPattern");
			if (!isEmpty(pattern)) {
				new InputMask(pattern, input);
			}
		} else if (Element.hasClassName(input, 'number')) {
			var parser = new NumberParser(0, dsep, ksep, false);
			new NumberMask(parser, input, input.maxLength || 9, false);
		} else if (Element.hasClassName(input, 'digits')) {
			new InputMask(maskBuilder.numbers(), input);
		} else if (Element.hasClassName(input, 'float')) {
			var mask = new NumberMask(numberParser, input, 9, false);
			mask.allowNegative = false;
		} else if (Element.hasClassName(input, 'floatAllowNegative')) {
			var mask = new NumberMask(numberParser, input, 9, false);
			mask.allowNegative = true;
		} else if (Element.hasClassName(input, 'floatHighPrecision')) {
			var mask = new NumberMask(highPrecisionParser, input, 9);
			mask.allowNegative = false;
			mask.leftToRight = true;
		} else if (Element.hasClassName(input, 'floatHighPrecisionAllowNegative')) {
			var mask = new NumberMask(highPrecisionParser, input, 9);
			mask.allowNegative = true;
			mask.leftToRight = true;
		} else if (Element.hasClassName(input, 'floatNegative')) {
			var mask = new NumberMask(numberParser, input);
			mask.keyUpFunction = function() {
				if (input.value == "") return;
				var number = mask.getAsNumber();
				if (number > 0) {
					input.value = "-" + input.value;
				}
			}
			mask.allowNegative = false;
		} else if (Element.hasClassName(input, 'date') || Element.hasClassName(input, 'dateNoLabel')) {
			new DateMask(dateParser, input);
			if (!Element.hasClassName(input, 'dateNoLabel')) {
				new Insertion.After(input, "&nbsp;(<span class='fieldDecoration' style='font-size:80%'>" + datePattern + "</span>)");
			}
			addCalendarButton(input);
		} else if (Element.hasClassName(input, 'dateTime') || Element.hasClassName(input, 'dateTimeNoLabel')) {
			// Use a new parser to not enforce length
			var parser = new DateParser(dateTimeParser.mask, false);
			parser._isValid = parser.isValid;
			var mask = new DateMask(parser, input);
			mask.blurFunction = function() {
				// Ensure at least the date part will be required
				var len = this.value.length;
				if (len > 0 && len < dateParser.mask.length) {
					alert(mask.validationMessage);
					this.value = '';
					return false;
				}
				// Fill in the missing fields on blur
	        	var controlValue = this.value.toUpperCase();
	        	controlValue = controlValue.replace(/A[^M]/, "AM");
	        	controlValue = controlValue.replace(/A$/, "AM");
	        	controlValue = controlValue.replace(/P[^M]/, "PM");
	        	controlValue = controlValue.replace(/P$/, "PM");
				var date = parser.parse(controlValue);
				if (date != null) {
					this.value = parser.format(date);
				} else {
					this.value = '';
				}
			}
			if (!Element.hasClassName(input, 'dateTimeNoLabel')) {
				new Insertion.After(input, "&nbsp;(<span class='fieldDecoration' style='font-size:80%'>" + dateTimePattern + "</span>)");
			}
			addCalendarButton(input);
		} else if (Element.hasClassName(input, 'upload')) {
			new Insertion.After(input, "&nbsp;(<span class='fieldDecoration'>" + uploadLimitText + " " + maxUploadSize + "</span>)");
		} else if (Element.hasClassName(input, 'textFormatRadio')) {
			input.onclick = function() {
				var textareaName = input.getAttribute("textareaName");
				var textarea = getObject(textareaName);
				var container = textarea.parentNode;
				input.fieldText = $('textOfField_' + textareaName);
				input.fieldEnvelope = $('envelopeOfField_' + textareaName);
				if (input.fieldText) {
					input.fieldText.hide();
				}
				if (input.fieldEnvelope) {
					input.fieldEnvelope.show();
				}
				if (booleanValue(input.value)) {
					if (!container.oldInnerHTML) {
						container.oldInnerHTML = container.innerHTML;
					}
					if (textarea.lastType == 'plain') {
						textarea.value = replaceAll(textarea.value, "\n", "<br>");
					}
					makeRichEditor(textarea);
				} else {
					if (container.oldInnerHTML) {
						var value = textarea.value;
						container.innerHTML = container.oldInnerHTML;
						textarea = container.getElementsByTagName("textarea")[0];
						textarea.value = replaceAll(value, "<br>", "\n").stripTags();
						textarea.lastType = 'plain';
					}
				}
			}
		} 
	},
	
	'textarea': function(textarea) {
		if (Element.hasClassName(textarea, "required")) {
			if (Element.hasClassName(textarea, "full")) {
				textarea.style.width = "97%";
			}
			new Insertion.After(textarea, "&nbsp;<span class='fieldDecoration' style='vertical-align:top'>*</span>");
		}
		if (Element.hasClassName(textarea, "maxLength")) {
			var maxLength = parseInt(textarea.getAttribute("maxLength"));
			if (!isNaN(maxLength) && maxLength > 0) {
				new SizeLimit(textarea, maxLength);
			}
		}
	},
	
	'select': function(select) {
		if (Element.hasClassName(select, "required")) {
			new Insertion.After(select, "&nbsp;<span class='fieldDecoration' style='vertical-align:top'>*</span>");
		}
		//On konqueror, when no option defines selected, the first one is not automatically marked as selected
		if (select.selectedIndex < 0 && select.options.length > 0) {
			select.selectedIndex = 0;
		}
	},
	
	'img': function(img) {
		if (Element.hasClassName(img, "help")) {
			img.title = helpTooltip;
			var page = img.getAttribute("helpPage");
			img.onclick = function() {
				showHelp(page, 330, 400, '', -20, 10);
			};
			img.onmouseover = function() {
				window.status = helpStatusPrefix + ": " + page;
			};
			img.onmouseout = function() {
				window.status = "";
			};
			setPointer(img);
		} else if (Element.hasClassName(img, "remove")) {
			img.title = removeTooltip;
		} else if (Element.hasClassName(img, "edit")) {
			img.title = editTooltip;
		} else if (Element.hasClassName(img, "view")) {
			img.title = viewTooltip;
		} else if (Element.hasClassName(img, "preview")) {
			img.title = previewTooltip;
		} else if (Element.hasClassName(img, "permissions")) {
			img.title = permissionsTooltip;
		} else if (Element.hasClassName(img, "print")) {
			img.title = printTooltip;
		} else if (Element.hasClassName(img, "exportCSV")) {
			img.title = exportCSVTooltip;
		}
	},
	
	'table.defaultTableContent': function(table) {
		var rows = table.rows;
		for (var i = 1; i < rows.length; i++) {
			var tr = rows[i];
			var tds = tr.getElementsByTagName("td");
			if (tds.length > 0) {
				Element.addClassName(tds[0], "innerBorder");
			}
		}

//		var row = table.insertRow(table.rows.length);
//		var tdLeft = row.insertCell(0);
//		tdLeft.className = "bottomLeft";
//
//		var tdRight = row.insertCell(1);
//		tdRight.className = "bottomRight";
	},
	
	'label': function(label) {
		if (Element.hasClassName(label, "required")) {
			new Insertion.After(label, "&nbsp;<span class='fieldDecoration' style='vertical-align:top'>*</span>");
		}
	}
};
Behaviour.register(headBehaviour);