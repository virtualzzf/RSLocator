__author__ = 'Nick Hirakawa'


from math import log
import math

R = 0.0


def score_BM25(n, f, qf, r, N, dl, avdl, k1, k2, b):
	K = compute_K(dl, avdl, k1, b)
	first = log( ( (r + 0.5) / (R - r + 0.5) ) / ( (n - r + 0.5) / (N - n - R + r + 0.5)) )
	second = ((k1 + 1) * f) / (K + f)
	third = ((k2+1) * qf) / (k2 + qf)
	return first * second * third


def compute_K(dl, avdl, k1, b):
	return k1 * ((1-b) + b * (float(dl)/float(avdl)) )