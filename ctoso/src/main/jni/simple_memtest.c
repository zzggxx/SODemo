#include <stdio.h>
#include <stdlib.h>

#define MAX_COUNT			256
#define MAX_MALLOC_SIZE		(4*1024*1024)

int simple_memtest(void)
{
	int ret = 0;
	unsigned int *data[MAX_COUNT];
	unsigned int count = 0;
	unsigned int i, j;

	for (i = 0; i < MAX_COUNT; i++) {
		data[i] = (unsigned int *)malloc(MAX_MALLOC_SIZE);
		if (!data[i])
			break;
	}
	count = i;

	for (i = 0; i < count; i++) {
		unsigned int *p = data[i];
		for (j = 0; j < MAX_MALLOC_SIZE/sizeof(unsigned int); j ++)
			p[j] = 0x5A5A1234 + j;
		printf("Memory test %3d: write %u bytes.\n", i, MAX_MALLOC_SIZE);
	}

	for (i = 0; i < count; i++) {
		unsigned int *p = data[i];
		for (j = 0; j < MAX_MALLOC_SIZE/sizeof(unsigned int); j++) {
			if (p[j] != (0x5A5A1234 + j)) {
				ret = -1;
				printf("Memory test %3d: error occurs!\n", i);
				break;
			}
		}
		printf("Memory test %3d: read %u bytes.\n", i, MAX_MALLOC_SIZE);
	}

	for (i = 0; i < count; i++) {
		free(data[i]);
	}

	return ret;
}

/*
int main(void)
{
	return simple_memtest();
}
*/
