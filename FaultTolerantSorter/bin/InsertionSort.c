/* insertion sort ascending order */

#include <jni.h>
#include <stdio.h>
#include "InsertionSort.h"

JNIEXPORT jintArray JNICALL Java_InsertionSort_memoryAccessesAndSortedArray (JNIEnv *env, jobject thisObj, jintArray inJNIArray) {
	jint *data = (*env)->GetIntArrayElements(env, inJNIArray, NULL);
	if (data == NULL) return NULL;
	jsize length = (*env)->GetArrayLength(env, inJNIArray);

	int i, j, temp;
	jint memoryAccesses = 0;
	for (i = 1; i < length; i++) {
		j = i;

		while (j > 0 && data[j] < data[j - 1]) {
			temp = data[j];
			data[j] = data[j - 1];
			data[j - 1] = temp;
			memoryAccesses = memoryAccesses + 6;

			j--;
		}
	}

	jint memAndData[length+1];
	memAndData[0] = memoryAccesses;

	for (i = 1; i < length + 1; i++) {
		memAndData[i] = data[i-1];
		memoryAccesses = memoryAccesses + 1;
	}

	jintArray result = (*env)->NewIntArray(env, length+1);  // allocate
	if (result == NULL) return NULL;
	(*env)->SetIntArrayRegion(env, result, 0 , length+1, memAndData);  // copy
	return result;
}
