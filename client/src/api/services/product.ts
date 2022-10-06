import { AxiosResponse } from 'axios'

import api from '../api'
import { CreateProductData, ListResponse, PaginationParams, ProductData, ProductResponse } from '..'

const createProductRequest = async (data: CreateProductData): Promise<AxiosResponse<ProductResponse, any>> => {
  const formData = new FormData()
  const productBlob = new Blob([JSON.stringify(data.product)], { type: 'application/json' })

  formData.append('product', productBlob)

  for (const image of data.imageFiles) {
    formData.append('imageFiles', image)
  }

  return await api.post('/products', formData)
}

const getProductRequest = async (code: number): Promise<AxiosResponse<ProductResponse, any>> =>
  await api.get(`/products/${code}`)

const getProductsRequest = async (paginationParams: PaginationParams): Promise<AxiosResponse<ListResponse<ProductResponse>, any>> => {
  const params = new URLSearchParams()

  Object.entries(paginationParams).forEach(([key, value]) => {
    params.set(key, value)
  })
  const isEmpty = Array.from(params.values()).length === 0

  return await api.get(`/products/${isEmpty ? '' : '?'}${params.toString()}`)
}

const updateProductRequest = async (code: number, data: ProductData): Promise<AxiosResponse<ProductResponse, any>> =>
  await api.put(`/products/${code}`, data)

const deleteProductRequest = async (code: number): Promise<AxiosResponse<string, any>> =>
  await api.delete(`/products/${code}`)

export {
  createProductRequest,
  getProductRequest,
  getProductsRequest,
  updateProductRequest,
  deleteProductRequest
}
